package com.wcw.backend.controller;

import com.wcw.backend.entity.OriginalPic;
import com.wcw.backend.result.Result;
import com.wcw.backend.service.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
