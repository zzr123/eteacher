package com.turing.eteacher.remote;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.turing.eteacher.base.BaseRemote;
import com.turing.eteacher.component.ReturnBody;
import com.turing.eteacher.model.CustomFile;
import com.turing.eteacher.service.IFileService;

@RestController
@RequestMapping("remote")
public class FileRemote extends BaseRemote {

	@Autowired
	private IFileService fileServiceImpl;
	
	/**
	 * 根据数据ID获取文件列表
	 * @param request
	 * @param dataId
	 * @return
	 */
//{
//	result : 'success',//成功success，失败failure
//	data : [
//		{
//			fileId : '文件ID'
//			dataId : '数据ID',
//			fileName : '文件名称'
//		}
//	],
//	msg : '提示信息XXX'
//}		
	@RequestMapping(value = "data/{dataId}/files", method = RequestMethod.GET)
	public ReturnBody getFiles(HttpServletRequest request,@PathVariable String dataId){
		try{
			List<CustomFile> list = fileServiceImpl.getListByDataId(dataId);
			return new ReturnBody(ReturnBody.RESULT_SUCCESS, list);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE, ReturnBody.ERROR_MSG);
		}
	}
	
	@RequestMapping(value = "files/{fileId}", method = RequestMethod.GET)
	public ReturnBody downloadFile(HttpServletRequest request, HttpServletResponse response,
			@PathVariable String fileId){
		InputStream is = null;
        OutputStream os = null;
		try{
			CustomFile customFile = fileServiceImpl.get(fileId);
			String pathRoot = request.getSession().getServletContext().getRealPath("");
			File file = new File(pathRoot + "/upload/" + customFile.getServerName());
			response.reset();
            response.addHeader("Content-Disposition", "attachment;filename="
                + new String(customFile.getFileName().getBytes(), "ISO8859-1"));
            response.addHeader("Content-Length", "" + file.length());
            response.setContentType("application/octet-stream");

            is = new BufferedInputStream(new FileInputStream(file));
            os = new BufferedOutputStream(response.getOutputStream());
            byte[] b = new byte[1024];
            int i = 0;
            while ((i = is.read(b)) > 0) {
                os.write(b, 0, i);
            }
			return null;
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ReturnBody(ReturnBody.RESULT_FAILURE, ReturnBody.ERROR_MSG);
		}
		finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (Exception e) {
            	e.printStackTrace();
            }
        }
	}
}
