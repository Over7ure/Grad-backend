package com.wcw.backend.service.impl;

import com.github.junrar.Archive;
import com.github.junrar.rarfile.FileHeader;
import com.wcw.backend.entity.OriginalPic;
import com.wcw.backend.mapper.ImageMapper;
import com.wcw.backend.service.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * 图片服务实现类
 * 提供图片上传、查询和删除等功能
 */
@Service
public class ImageServiceImpl implements IImageService {
	@Autowired
	private ImageMapper imageMapper;

	/**
	 * 上传图片
	 *
	 * @param picUrl  图片的Base64编码字符串
	 * @param picName 图片名称
	 * @return 上传是否成功
	 */
	@Override
	public boolean uploadImage(String picUrl, String picName) {
		try {
			// 如果目标目录不存在则创建
			String absolutePath = "front-end/public/images/manually-upload/";
			String destDir = "images/manually-upload/";
			File directory = new File(absolutePath);
			if (!directory.exists()) {
				directory.mkdirs();
			}

			// 生成带时间戳的唯一文件名
			String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
			String uniqueFileName = timestamp + "_" + picName;

			// 解码Base64字符串
			String base64Image = picUrl;
			if (base64Image.contains(",")) {
				base64Image = base64Image.substring(base64Image.indexOf(",") + 1);
			}

			// 将Base64解码为二进制数据
			byte[] imageBytes = Base64.getDecoder().decode(base64Image);

			// 将二进制数据写入文件
			Path destination = Paths.get(absolutePath + uniqueFileName);
			Files.write(destination, imageBytes);

			// 保存图片信息到数据库
			OriginalPic image = new OriginalPic();
			image.setPicName(picName); // 存储原始文件名用于显示
			image.setPicUrl(destDir + uniqueFileName); // 存储相对路径
			imageMapper.save(image);

			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 查询所有图片
	 *
	 * @return 图片列表
	 */
	@Override
	public List<OriginalPic> findAll() {
		return imageMapper.findAll();
	}

	/**
	 * 分页查询图片
	 *
	 * @param pageNum  页码
	 * @param pageSize 每页数量
	 * @param picName  图片名称（模糊查询）
	 * @return 包含数据和总数的Map
	 */
	@Override
	public Map<String, Object> findPage(Integer pageNum, Integer pageSize, String picName) {
		pageNum = (pageNum - 1) * pageSize;
		picName = "%" + picName + "%";
		List<OriginalPic> list = imageMapper.selectPage(pageNum, pageSize, picName);
		Integer count = imageMapper.selectTotal(picName);
		Map<String, Object> res = new HashMap<>();
		res.put("data", list);
		res.put("total", count);
		return res;
	}

	/**
	 * 根据ID删除图片
	 *
	 * @param picId 图片ID
	 * @return 删除是否成功
	 */
	@Override
	public boolean deleteById(Integer picId) {
		// 删除前获取图片信息
		OriginalPic image = imageMapper.findById(picId);
		if (image == null) {
			return false;
		}

		try {
			// 从数据库获取文件路径
			String relativePath = image.getPicUrl();
			// 转换为物理文件操作的绝对路径
			String absolutePath = "front-end/public/" + relativePath;

			// 删除物理文件
			File file = new File(absolutePath);
			if (file.exists()) {
				boolean fileDeleted = file.delete();
				if (!fileDeleted) {
					System.err.println("删除文件失败: " + absolutePath);
					// 即使文件删除失败，仍继续删除数据库记录
				}
			}

			// 删除数据库记录
			return imageMapper.deleteById(picId);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 处理上传的压缩包
	 *
	 * @param file 上传的压缩包文件
	 * @return 包含处理成功和失败数量的Map
	 * @throws Exception 处理过程中的异常
	 */
	@Override
	public Map<String, Object> processArchive(MultipartFile file) throws Exception {
		Map<String, Object> result = new HashMap<>();
		int successCount = 0;
		int failedCount = 0;

		// 创建临时目录存储解压的文件
		String tempDirPath = System.getProperty("java.io.tmpdir") + "/archive_extract_" + System.currentTimeMillis();
		File tempDir = new File(tempDirPath);
		if (!tempDir.exists() && !tempDir.mkdirs()) {
			throw new IOException("无法创建临时目录");
		}

		try {
			// 将上传的文件保存到临时文件
			String originalFilename = file.getOriginalFilename();
			if (originalFilename == null) {
				throw new IllegalArgumentException("文件名不能为空");
			}

			File tempFile = new File(tempDir, originalFilename);
			file.transferTo(tempFile);

			// 解压文件
			if (originalFilename.toLowerCase().endsWith(".zip")) {
				unzipFile(tempFile, tempDir);
			} else if (originalFilename.toLowerCase().endsWith(".rar")) {
				unrarFile(tempFile, tempDir);
			} else {
				throw new IllegalArgumentException("不支持的压缩包格式");
			}

			// 处理解压后的文件
			Map<String, Integer> counts = processExtractedFiles(tempDir);
			successCount = counts.get("success");
			failedCount = counts.get("failed");
		} finally {
			// 清理临时文件和目录
			deleteDirectory(tempDir);
		}

		result.put("success", successCount);
		result.put("failed", failedCount);
		return result;
	}

	/**
	 * 解压ZIP文件
	 *
	 * @param zipFile ZIP文件
	 * @param destDir 目标目录
	 * @throws IOException IO异常
	 */
	private void unzipFile(File zipFile, File destDir) throws IOException {
		try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile))) {
			ZipEntry zipEntry;
			byte[] buffer = new byte[1024];

			while ((zipEntry = zis.getNextEntry()) != null) {
				String fileName = zipEntry.getName();
				// 忽略__MACOSX目录和隐藏文件
				if (fileName.startsWith("__MACOSX") || fileName.startsWith(".")) {
					continue;
				}

				File newFile = new File(destDir, fileName);

				// 创建目录
				if (zipEntry.isDirectory()) {
					if (!newFile.isDirectory() && !newFile.mkdirs()) {
						throw new IOException("无法创建目录: " + newFile);
					}
					continue;
				}

				// 创建父目录
				File parent = newFile.getParentFile();
				if (!parent.isDirectory() && !parent.mkdirs()) {
					throw new IOException("无法创建目录: " + parent);
				}

				// 写入文件
				try (FileOutputStream fos = new FileOutputStream(newFile)) {
					int len;
					while ((len = zis.read(buffer)) > 0) {
						fos.write(buffer, 0, len);
					}
				}
			}
		}
	}

	/**
	 * 解压RAR文件, 使用junrar库处理
	 *
	 * @param rarFile RAR文件
	 * @param destDir 目标目录
	 * @throws Exception 处理异常
	 */
	private void unrarFile(File rarFile, File destDir) throws Exception {
		// 使用junrar进行解压
		try (Archive archive = new Archive(rarFile)) {
			if (archive == null) {
				throw new IOException("无法读取RAR文件");
			}
			FileHeader fh = archive.nextFileHeader();
			while (fh != null) {
				String fileName = fh.getFileNameString().trim();
				File outFile = new File(destDir, fileName);
				if (fh.isDirectory()) {
					if (!outFile.exists() && !outFile.mkdirs()) {
						throw new IOException("无法创建目录: " + outFile);
					}
				} else {
					File parent = outFile.getParentFile();
					if (!parent.exists() && !parent.mkdirs()) {
						throw new IOException("无法创建目录: " + parent);
					}
					try (OutputStream os = new FileOutputStream(outFile)) {
						archive.extractFile(fh, os);
					}
				}
				fh = archive.nextFileHeader();
			}
		}
	}

	/**
	 * 处理解压后的文件
	 *
	 * @param directory 包含解压文件的目录
	 * @return 包含成功和失败计数的Map
	 * @throws IOException IO异常
	 */
	private Map<String, Integer> processExtractedFiles(File directory) throws IOException {
		int successCount = 0;
		int failedCount = 0;

		List<File> files = getAllFiles(directory);

		for (File file : files) {
			// 检查是否为支持的图片格式
			String fileName = file.getName().toLowerCase();
			if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") ||
					fileName.endsWith(".png") || fileName.endsWith(".gif") ||
					fileName.endsWith(".ico") || fileName.endsWith(".bmp")) {

				try {
					// 读取图片文件
					byte[] fileContent = Files.readAllBytes(file.toPath());

					// 获取MIME类型
					String mimeType = determineMimeType(fileName);

					// 转换为Base64
					String base64Image = Base64.getEncoder().encodeToString(fileContent);
					String base64WithPrefix = "data:" + mimeType + ";base64," + base64Image;

					// 调用已有的上传方法
					if (uploadImage(base64WithPrefix, file.getName())) {
						successCount++;
					} else {
						failedCount++;
					}
				} catch (Exception e) {
					e.printStackTrace();
					failedCount++;
				}
			}
		}

		Map<String, Integer> result = new HashMap<>();
		result.put("success", successCount);
		result.put("failed", failedCount);
		return result;
	}

	/**
	 * 根据文件扩展名确定MIME类型
	 *
	 * @param fileName 文件名
	 * @return MIME类型
	 */
	private String determineMimeType(String fileName) {
		if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
			return "image/jpeg";
		} else if (fileName.endsWith(".png")) {
			return "image/png";
		} else if (fileName.endsWith(".gif")) {
			return "image/gif";
		} else if (fileName.endsWith(".ico")) {
			return "image/x-icon";
		} else if (fileName.endsWith(".bmp")) {
			return "image/bmp";
		}
		return "application/octet-stream";
	}

	/**
	 * 递归获取目录中的所有文件
	 *
	 * @param directory 目录
	 * @return 文件列表
	 */
	private List<File> getAllFiles(File directory) {
		List<File> files = new ArrayList<>();
		File[] fileList = directory.listFiles();

		if (fileList != null) {
			for (File file : fileList) {
				if (file.isDirectory()) {
					files.addAll(getAllFiles(file));
				} else {
					files.add(file);
				}
			}
		}

		return files;
	}

	/**
	 * 递归删除目录及其内容
	 *
	 * @param directory 要删除的目录
	 */
	private void deleteDirectory(File directory) {
		if (directory.exists()) {
			File[] files = directory.listFiles();
			if (files != null) {
				for (File file : files) {
					if (file.isDirectory()) {
						deleteDirectory(file);
					} else {
						file.delete();
					}
				}
			}
			directory.delete();
		}
	}
}

