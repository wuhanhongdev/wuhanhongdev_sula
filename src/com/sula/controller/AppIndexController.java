package com.sula.controller;

import com.fengpei.ioc.Controller;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;
import com.sula.config.Config;
import com.sula.service.ComplainService;
import com.sula.service.DriverService;
import com.sula.service.GoodsService;
import com.sula.service.LoginService;
import com.sula.util.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * App首页接口
 */
public class AppIndexController extends Controller {
    LoginService loginService;
    GoodsService goodsService;
    DriverService driverService;
    ComplainService complainService;
    /**
     * App首页
     */
    public void index(){

    }

    public void queryDicts(){
        ResultJson json = new ResultJson();
        String dictType = getPara("dictType");
        String sign = getPara("sign");
        if (StringUtils.isEmpty(sign)){
            json.setCode(Status.fail);
            json.setMessage("手机MAC地址为空");
        }else if(StringUtils.isEmpty(dictType)){
            json.setCode(Status.fail);
            json.setMessage("字典类型不能为空");
        }else{
            if(Verify.isMac(sign)){
                List<Record> records = loginService.queryDicts(dictType);
                if (records != null) {
                    json.setCode(Status.success);
                    json.setMessage(Status.message_success);
                    json.setResult(records);
                } else {
                    json.setCode(Status.fail);
                    json.setMessage(Status.message_null);
                }
            }else{
                json.setCode(Status.fail);
                json.setMessage("校验码错误");
            }
        }

        renderJson(json);
    }

    /**
     * 上传头像
     */

    /**
     * 建议和反馈
     */

    /**
     * 查询我的运单
     * 运单状态：0-待支付  1-待装货 2-运单中  3-待收货 4-待评价 5-完成  10-取消
     * 对于货主：我的运单包含了我发布的货源：发布时状态为派单中  若有司机接单  则以运单表为准
     * 对于司机：我的运单仅包含了运单表中有的内容
     */
    public void loadMimeWaybill(){
        ResultJson json = new ResultJson();

        String userRole = getPara("userRole");
        Integer userId = getParaToInt("userId");
        String type = getPara("type");
        String sign = getPara("sign");
        if (StringUtils.isEmpty(sign)){
            json.setCode(Status.fail);
            json.setMessage("手机MAC地址为空");
        }else if(StringUtils.isEmpty(userRole)){
            json.setCode(Status.fail);
            json.setMessage("用户类型为空");
        }else if(userId == null){
            json.setCode(Status.fail);
            json.setMessage("用户ID为空");
        }else if(StringUtils.isEmpty(type)){
            json.setCode(Status.fail);
            json.setMessage("查询类型为空");
        }else{
            if(Verify.isMac(sign)){
                Record record = null;
                if("2".equals(userRole)){//查询货主运单
                    record = goodsService.selectMimeWaybill(userId,type);
                }else if("1".equals(userRole)){//查询司机运单
                    record = driverService.selectMimeWaybill(userId,type);
                }
                json.setCode(Status.success);
                json.setMessage("查询成功");
                json.setResult(record);
            }else{
                json.setCode(Status.fail);
                json.setMessage("校验码错误");
            }
        }
        renderJson(json);
    }

    /**
     * 投诉
     * 添加投诉时状态默认为未处理
     * 默认在APP显示处理结果
     * 投诉时间默认为系统时间
     */
    public void addComplain(){
        ResultJson json = new ResultJson();
        Integer type = getParaToInt("type");
        Integer sourceId = getParaToInt("sourceId");
        Integer userId = getParaToInt("userId");
        String mobile = getPara("mobile");
        String content = getPara("content");
        String sign = getPara("sign");
        if (StringUtils.isEmpty(sign)){
            json.setCode(Status.fail);
            json.setMessage("手机MAC地址为空");
        }else if(type == null){
            json.setCode(Status.fail);
            json.setMessage("投诉类型不能为空");
        }else if(sourceId == null){
            json.setCode(Status.fail);
            json.setMessage("车源或者货源ID不能为空");
        }else if(userId == null){
            json.setCode(Status.fail);
            json.setMessage("投诉人ID不能为空");
        }else if(StringUtils.isEmpty(content)){
            json.setCode(Status.fail);
            json.setMessage("投诉内容不能为空");
        }else{
            if(Verify.isMac(sign)){
                int state = complainService.addComplain(type,sourceId,userId,mobile,content);
                if(state == Status.fail){
                    json.setCode(Status.fail);
                    json.setMessage("评论失败!");
                }else{
                    json.setCode(Status.success);
                    json.setMessage("评论成功");
                }
            }else{
                json.setCode(Status.fail);
                json.setMessage("校验码错误");
            }
        }
        renderJson(json);
    }
    /**
     * 货主认证
     */
    public void shipperVerify(){
        ResultJson json = new ResultJson();
        try{
            UploadFile idcard = getFile("idcard");
            UploadFile businessLicense = getFile("businessLicense");
            Integer userId = getParaToInt("userId");
            String sign = getPara("sign");
            if(StringUtils.isEmpty(sign)){
                json.setCode(Status.fail);
                json.setMessage("手机MAC地址为空");
            }else if(idcard == null){
                json.setCode(Status.fail);
                json.setMessage("身份证照片不能为空");
            }else if(businessLicense == null){
                json.setCode(Status.fail);
                json.setMessage("营业执照照片不能为空");
            }else if(userId == null){
                json.setCode(Status.fail);
                json.setMessage("用户ID不能为空");
            }else{
                if(Verify.isMac(sign)){
                    //处理身份证
                    String idcardFileName = idcard.getFileName();
                    String extentionName = idcardFileName.substring(idcardFileName.lastIndexOf(".")); // 后缀名
                    String idcardNewName = GenerateSequenceUtil.generateSequenceNo() + extentionName;// 新名
                    String filePath = Config.relativePath + "UserImgs" + File.separator +idcardNewName;
                    FileUtils.copyFile(idcard.getFile(),new File(filePath));
                    //处理营业执照
                    String businessLicenseFileName = businessLicense.getFileName();
                    String businessLicenseExtentionName = idcardFileName.substring(businessLicenseFileName.lastIndexOf(".")); // 后缀名
                    String businessLicenseNewName = GenerateSequenceUtil.generateSequenceNo() + businessLicenseExtentionName;// 新名
                    String businessLicenseFilePath = Config.relativePath  + "UserImgs" + File.separator + businessLicenseNewName;
                    FileUtils.copyFile(businessLicense.getFile(),new File(businessLicenseFilePath));

                    Record record = new Record();
                    record.set("user_id",userId);
                    record.set("img1",idcardNewName);
                    record.set("img2",businessLicenseNewName);
                    record.set("type",1);//设置为待审核
                    record.set("create_time",new Date());

                    int state = goodsService.shipperVerify(record);

                    if(state == Status.success){
                        json.setCode(Status.success);
                        json.setMessage("实名认证提交成功,请等待审核!");
                    }else{
                        json.setCode(Status.fail);
                        json.setMessage("实名认证提交失败,请重试");
                    }
                }else{
                    json.setCode(Status.fail);
                    json.setMessage("校验码错误");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            json.setCode(Status.fail);
            json.setMessage("图片获取失败");
        }
        renderJson(json);
    }

    /**
     * 司机认证
     */
    public void driverVerify(){
        ResultJson json = new ResultJson();
        try{
            UploadFile idcard = getFile("idcard");
            UploadFile driverLicense = getFile("driverLicense");
            UploadFile holdingPhoto = getFile("holdingPhoto");
            Integer userId = getParaToInt("userId");
            String sign = getPara("sign");
            if(StringUtils.isEmpty(sign)){
                json.setCode(Status.fail);
                json.setMessage("手机MAC地址为空");
            }else if(idcard == null){
                json.setCode(Status.fail);
                json.setMessage("身份证照片不能为空");
            }else if(driverLicense == null){
                json.setCode(Status.fail);
                json.setMessage("驾驶证照片不能为空");
            }else if(holdingPhoto == null){
                json.setCode(Status.fail);
                json.setMessage("手持照片不能为空");
            }else if(userId == null){
                json.setCode(Status.fail);
                json.setMessage("用户ID不能为空");
            }else{
                if(Verify.isMac(sign)){
                    //处理身份证
                    String idcardFileName = idcard.getFileName();
                    String extentionName = idcardFileName.substring(idcardFileName.lastIndexOf(".")); // 后缀名
                    String idcardNewName = GenerateSequenceUtil.generateSequenceNo() + extentionName;// 新名
                    String filePath = Config.relativePath + "UserImgs" + File.separator + idcardNewName;
                    FileUtils.copyFile(idcard.getFile(),new File(filePath));
                    //处理驾驶证
                    String driverLicenseFileName = driverLicense.getFileName();
                    String driverLicenseExtentionName = idcardFileName.substring(driverLicenseFileName.lastIndexOf(".")); // 后缀名
                    String driverLicenseNewName = GenerateSequenceUtil.generateSequenceNo() + driverLicenseExtentionName;// 新名
                    String driverLicenseFilePath = Config.relativePath + "UserImgs" + File.separator + driverLicenseNewName;
                    FileUtils.copyFile(driverLicense.getFile(),new File(driverLicenseFilePath));
                    //处理手持照片
                    String holdingPhotoFileName = holdingPhoto.getFileName();
                    String holdingPhotoExtentionName = idcardFileName.substring(holdingPhotoFileName.lastIndexOf(".")); // 后缀名
                    String holdingPhotoNewName = GenerateSequenceUtil.generateSequenceNo() + holdingPhotoExtentionName;// 新名
                    String holdingPhotoFilePath = Config.relativePath + "UserImgs" + File.separator + holdingPhotoNewName;
                    FileUtils.copyFile(driverLicense.getFile(),new File(holdingPhotoFilePath));

                    Record record = new Record();
                    record.set("user_id",userId);
                    record.set("img1",idcardNewName);
                    record.set("img3",driverLicenseNewName);
                    record.set("img4",holdingPhotoNewName);
                    record.set("type",1);//设置为待审核
                    record.set("create_time",new Date());

                    int state = driverService.driverVerify(record);

                    if(state == Status.success){
                        json.setCode(Status.success);
                        json.setMessage("实名认证提交成功,请等待审核!");
                    }else{
                        json.setCode(Status.fail);
                        json.setMessage("实名认证提交失败,请重试");
                    }
                }else{
                    json.setCode(Status.fail);
                    json.setMessage("校验码错误");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            json.setCode(Status.fail);
            json.setMessage("图片获取失败");
        }
        renderJson(json);
    }

    /**
     * 获取轮播图
     */
    public void banners(){
        List<Record> banners = loginService.loadBanners();
        ResultJson json = new ResultJson();
        if(banners != null && banners.size() > 0){
            json.setCode(Status.success);
            json.setMessage("轮播图加载成功");
            json.setResult(banners);
        }else{
            json.setCode(Status.fail);
            json.setMessage("轮播图加载失败");
        }
        renderJson(json);
    }

    /**
     * App登录注册
     */
    public void login(){
        //电话
        String mobile = getPara("mobile");
        String sign = getPara("sign");
        //邀请码
        String invitationCode = getPara("invitationCode");
        //短信验证码
        String messageCode = getPara("messageCode");
        ResultJson json = new ResultJson();
        if (StringUtils.isEmpty(mobile)){
            json.setCode(Status.fail);
            json.setMessage("手机号码不能为空");
        }else if(StringUtils.isEmpty(sign)){
            json.setCode(Status.fail);
            json.setMessage("手机MAC地址为空");
        }else if(StringUtils.isEmpty(messageCode)){
            json.setCode(Status.fail);
            json.setMessage("短信验证码不能为空");
        }else{
            if(Verify.isMac(sign)){
                //校验都通过
                Record record = loginService.login(mobile,messageCode);
                if(record == null){
                    json.setCode(Status.fail);
                    json.setMessage("手机号码错误或者短信验证码错误");
                }else{
                    json.setCode(Status.success);
                    json.setMessage("登录成功");
                    json.setResult(record);
                }
            }else{
                json.setCode(Status.fail);
                json.setMessage("校验码错误");
            }
        }
        //将处理对象返回出去
        renderJson(json);
    }

    /**
     * 短信验证码发送,短信发送后，将发送的code存入用户表中
     */
    public void sendVerifyCode(){
        Map<String,String[]> parameterMap = getRequest().getParameterMap();
        //电话
        String mobile = getPara("mobile");
        String sign = getPara("sign");
        ResultJson json = new ResultJson();
        if (StringUtils.isEmpty(mobile)){
            json.setCode(Status.fail);
            json.setMessage("手机号码不能为空");
        }else if(StringUtils.isEmpty(sign)){
            json.setCode(Status.fail);
            json.setMessage("手机MAC地址为空");
        }else{
            if(Verify.isMac(sign)){
                String vcode = createRandomVcode();

                SmsUtil.exece(mobile,"你好，我是短信小助手【速拉】， "+vcode+" 是您的验证码");
                //发送成功后将其新增入库
                Record record = new Record();
                record.set("mobile",mobile).set("password",vcode).set("create_time",new Date()).set("balance",0.00);

                int resultNum = loginService.saveLoginUser(record);

                if(Status.success == resultNum){
                    json.setCode(Status.success);
                    json.setMessage("验证码获取成功");
                    //json.setResult("{vcode:"+vcode+"}");
                }else{
                    json.setCode(Status.fail);
                    json.setMessage("验证码获取失败,请重试");
                }
            }else{
                json.setCode(Status.fail);
                json.setMessage("校验码错误");
            }
        }

        renderJson(json);
    }

    /**
     * 生成6位数字验证码
     * @return
     */
    public static String createRandomVcode(){
        //验证码
        String vcode = "";
        for (int i = 0; i < 6; i++) {
            vcode = vcode + (int)(Math.random() * 9);
        }
        return vcode;
    }
}
