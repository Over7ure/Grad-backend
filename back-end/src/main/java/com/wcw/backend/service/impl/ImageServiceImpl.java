package com.wcw.backend.service.impl;

import com.wcw.backend.entity.OriginalPic;
import com.wcw.backend.mapper.ImageMapper;
import com.wcw.backend.service.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	 * @param picUrl 图片的Base64编码字符串
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
	 * @return 图片列表
	 */
	@Override
	public List<OriginalPic> findAll() {
		return imageMapper.findAll();
	}

	/**
	 * 分页查询图片
	 * @param pageNum 页码
	 * @param pageSize 每页数量
	 * @param picName 图片名称（模糊查询）
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
}
