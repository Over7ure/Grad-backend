package com.wcw.backend.controller;

import com.wcw.backend.entity.OriginalPic;
import com.wcw.backend.result.Result;
import com.wcw.backend.service.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 图片管理控制器
 * 处理图片上传、查询、删除等操作
 */
@RestController
@RequestMapping("/api/image")
public class ImageController {

	@Autowired
	private IImageService imageService;

	/**
	 * 手动上传图片
	 * 
	 * @param requestBody 包含图片URL和名称的请求体
	 * @return 操作结果
	 */
	@PostMapping("/uploadManually")
	public Result uploadManually(@RequestBody Map<String, String> requestBody) {
		String picUrl = requestBody.get("picUrl");
		String picName = requestBody.get("picName");

		if (picUrl == null || picName == null) {
			return new Result(400, "Missing required parameters");
		}

		return imageService.uploadImage(picUrl, picName)
				? new Result(200, "Image uploaded successfully")
				: new Result(500, "Failed to upload image");
	}

	/**
	 * 查询所有图片
	 * 
	 * @return 图片列表
	 */
	@GetMapping("/findAll")
	public List<OriginalPic> findAll() {
		return imageService.findAll();
	}

	/**
	 * 分页查询图片
	 * 
	 * @param pageNum 页码
	 * @param pageSize 每页数量
	 * @param picName 图片名称(用于模糊查询)
	 * @return 分页结果
	 */
	@GetMapping("/findPage")
	public Map<String, Object> findPage(@RequestParam Integer pageNum,
	                                    @RequestParam Integer pageSize,
	                                    @RequestParam String picName) {
		return imageService.findPage(pageNum, pageSize, picName);
	}

	/**
	 * 根据ID删除图片
	 * 
	 * @param picId 图片ID
	 * @return 操作结果
	 */
	@DeleteMapping("/delete/{picId}")
	public Result deleteById(@PathVariable int picId) {
		return imageService.deleteById(picId)
				? new Result(200, "Image deleted successfully")
				: new Result(500, "Failed to delete image");
	}

	/**
	 * 上传压缩包并处理其中的图片
	 *
	 * @param file 压缩包文件(zip或rar格式)
	 * @return 操作结果
	 */
	@PostMapping("/uploadArchive")
	public Result uploadArchive(@RequestParam("file") MultipartFile file) {
		if (file == null || file.isEmpty()) {
			return new Result(400, "压缩包文件不能为空");
		}

		// 获取原始文件名
		String originalFilename = file.getOriginalFilename();
		if (originalFilename == null || (!originalFilename.endsWith(".zip") && !originalFilename.endsWith(".rar"))) {
			return new Result(400, "只支持ZIP和RAR格式的压缩包");
		}

		try {
			// 调用service层方法处理压缩包
			Map<String, Object> result = imageService.processArchive(file);
			int successCount = (int) result.get("success");
			int failedCount = (int) result.get("failed");

			// 根据处理结果返回不同的消息
			if (failedCount == 0) {
				return new Result(200, "成功从压缩包中提取并上传了" + successCount + "张图片");
			} else if (successCount > 0) {
				return new Result(200, "成功上传" + successCount + "张图片，" + failedCount + "张图片处理失败");
			} else {
				return new Result(500, "无法从压缩包中提取任何图片");
			}
		} catch (Exception e) {
			return new Result(500, "处理压缩包时发生错误: " + e.getMessage());
		}
	}
}
