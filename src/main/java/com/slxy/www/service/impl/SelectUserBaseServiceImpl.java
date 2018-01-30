package com.slxy.www.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.slxy.www.common.Constant;
import com.slxy.www.common.ExcelUtil;
import com.slxy.www.common.utils.SelectMapStructMapper;
import com.slxy.www.mapper.SelectDepartmentMapper;
import com.slxy.www.mapper.SelectMajorMapper;
import com.slxy.www.mapper.SelectUserBaseMapper;
import com.slxy.www.model.SelectDepartment;
import com.slxy.www.model.SelectMajor;
import com.slxy.www.model.SelectUserBase;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.slxy.www.model.vo.ImportStuVo;
import com.slxy.www.model.vo.ImportTeaVo;
import com.slxy.www.model.vo.SelectUserBaseVo;
import com.slxy.www.model.dto.SelectUserBaseDto;
import com.slxy.www.model.enums.*;
import com.slxy.www.service.ISelectUserBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhengql
 * @since 2018-01-06
 */
@Service
public class SelectUserBaseServiceImpl extends ServiceImpl<SelectUserBaseMapper, SelectUserBase> implements ISelectUserBaseService{
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SelectUserBaseMapper selectUserBaseMapper;
    @Autowired
    private SelectMajorMapper selectMajorMapper;
    @Autowired
    private SelectDepartmentMapper selectDepartmentMapper;

    @Autowired
    private DataSourceTransactionManager dsTransactionManager;




    @Override
    public ModelAndView userList(ModelAndView  modelAndView ,SelectUserBaseVo userBaseVo) {
        Page<SelectUserBaseDto> page = new Page<>(userBaseVo.getPage(),userBaseVo.getPageSize());
        List<SelectUserBase> userList = selectUserBaseMapper.getUserByPage(page,userBaseVo);
        List<SelectUserBaseDto> userBaseDto = SelectMapStructMapper.INSTANCE.SelectUserBasesPo2Dto(userList);
        setUserDto(userBaseDto);
        page.setRecords(userBaseDto);
        List<SelectUserBase> yearList = selectUserBaseMapper.selectStuYear();
        List<SelectUserBase> majorList = selectUserBaseMapper.selectStuMajor();
        List<SelectUserBase> classList = selectUserBaseMapper.selectStuClass();
        List<SelectUserBase> depList = selectUserBaseMapper.selectTeaDep();
        List<SelectDepartment> teaDepList = selectDepartmentMapper.selectTeaDep();
        modelAndView.addObject("userList", userBaseDto);
        modelAndView.addObject("page",page);
        modelAndView.addObject("yearList",yearList);
        modelAndView.addObject("majorList",majorList);
        modelAndView.addObject("classList",classList);
        modelAndView.addObject("depList",depList);
        modelAndView.addObject("teaDepList",teaDepList);
        modelAndView.addObject("teaPosition",EnumTeaPosition.toMap());
        modelAndView.addObject("teaEducation",EnumTeaEducation.toMap());
        return modelAndView;
    }

    /**
     * 设置用户其他属性
     * @param userBaseDto
     */
    private void setUserDto(List<SelectUserBaseDto> userBaseDto) {
        for (SelectUserBaseDto dto:userBaseDto){
            //性别
            dto.setSex(EnumUserSex.toMap().get(dto.getUserSex()));
            if (!ObjectUtils.isEmpty(dto.getTeaPosition())){
                //职称
                dto.setTeaPositionZ(EnumTeaPosition.toMap().get(dto.getTeaPosition()));
            }
            if (!ObjectUtils.isEmpty(dto.getTeaEducation())){
                //学历
                dto.setTeaEducationZ(EnumTeaEducation.toMap().get(dto.getTeaEducation()));
            }
        }
    }

    /**
     * 设置单个用户其他属性
     * @param dto
     */
    private void setUserDto(SelectUserBaseDto dto) {

            dto.setSex(EnumUserSex.toMap().get(dto.getUserSex()));
            if (!ObjectUtils.isEmpty(dto.getTeaPosition())){
                //职称
                dto.setTeaPositionZ(EnumTeaPosition.toMap().get(dto.getTeaPosition()));
            }
            if (!ObjectUtils.isEmpty(dto.getTeaEducation())){
                //学历
                dto.setTeaEducationZ(EnumTeaEducation.toMap().get(dto.getTeaEducation()));
            }
    }

    /***
     * 异步生成学生列表
     * @param userBaseVo
     * @return
     */
    @Override
    public String stuListAjax(SelectUserBaseVo userBaseVo) {
        Page<SelectUserBaseDto> page = new Page<>(userBaseVo.getPage(),userBaseVo.getPageSize());
        List<SelectUserBase> userList = selectUserBaseMapper.getUserByPage(page,userBaseVo);
        List<SelectUserBaseDto> userBaseDto = SelectMapStructMapper.INSTANCE.SelectUserBasesPo2Dto(userList);
        setUserDto(userBaseDto);
        Map<String,Object> map = new HashMap<>();
        map.put("page",page);
        map.put("stuList",userBaseDto);
        String object = JSONObject.toJSONString(map);
        return object;
    }

    /**
     * 学生启用禁用
     * 存在选题记录不可禁用
     * 专业禁用不可启用
     * @param userBaseVo
     * @return
     */
    @Override
    public String stuAble(SelectUserBaseVo userBaseVo) {
        SelectUserBase userBase = this.selectById(userBaseVo.getId());
        SelectMajor selectMajor = selectMajorMapper.selectOne(new SelectMajor().setMajName(userBase.getStuMajorName()));
        //判断所属专业
        if (ObjectUtils.isEmpty(selectMajor)||selectMajor.getMajStatus().equals(EnumEnOrDis.DISABLED.getValue())){
            logger.info(Constant.STU_ABLE_ERROR_MAJ_DISABLE);
            return Constant.STU_ABLE_ERROR_MAJ_DISABLE;
        }
        //todo 判断选题记录是否完成

        SelectUserBase selectUserBase = new SelectUserBase()
                .setId(userBaseVo.getId())
                .setUserStatus(userBaseVo.getUserStatus());
        return this.updateById(selectUserBase) ? Constant.SUCCESS : Constant.ERROR;
    }

    /**
     * 删除学生记录
     * 存在选题记录不可删除
     * @param userBaseVo
     * @return
     */
    @Override
    public String stuDelete(SelectUserBaseVo userBaseVo) {
        //todo 存在未完成选题记录不可删除
        SelectUserBase selectUserBase = new SelectUserBase()
                .setId(userBaseVo.getId())
                .setUserStatus(userBaseVo.getUserStatus());
        return this.deleteById(selectUserBase) ? Constant.SUCCESS : Constant.ERROR;
    }

    /***
     * 批量删除学生
     * @param selectedIDs
     * @return
     */
    @Override
    public String stuDeleteAll(Integer[] selectedIDs) {
        //todo 存在未完成的选题记录不可删除
        for (Integer id: selectedIDs){
            SelectUserBase selectUserBase = new SelectUserBase().setId(id);

        }
        return this.deleteBatchIds(Arrays.asList(selectedIDs))?Constant.SUCCESS:Constant.ERROR;
    }



    /**
     * 初始化编辑页面
     * 返回学生信息
     * @param modelAndView
     * @param userBaseVo
     * @return
     */
    @Override
    public ModelAndView stuInitAddAndUpdate(ModelAndView modelAndView, SelectUserBaseVo userBaseVo) {
        SelectUserBase userBase = this.selectById(userBaseVo.getId());
        if (!ObjectUtils.isEmpty(userBase)){
            modelAndView.addObject("user",userBase);
        }
        List<SelectUserBase> yearList = selectUserBaseMapper.selectStuYear();
        List<SelectUserBase> majorList = selectUserBaseMapper.selectStuMajor();
        List<SelectUserBase> classList = selectUserBaseMapper.selectStuClass();
        modelAndView.addObject("yearList",yearList);
        modelAndView.addObject("majorList",majorList);
        modelAndView.addObject("classList",classList);
        return modelAndView;
    }

    /**
     * 学生修改
     * @param userBase
     * @return
     */
    @Override
    public String stuUpdate(SelectUserBase userBase) {
        //校验重复
        String msg = checkCodeAndName(userBase);
        if (StringUtils.isEmpty(msg)) return msg;
        return this.updateById(userBase)?Constant.SUCCESS:Constant.ERROR;
    }


    /**
     * 学生添加
     * @param user
     * @return
     */
    @Override
    public String stuAdd(SelectUserBase user) {
        if (ObjectUtils.isEmpty(user)){
            return  Constant.ERROR;
        }
        String msg = checkCodeAndName(user);
        if (!StringUtils.isEmpty(msg)) return msg;
        user.setGmtCreate(new Date())
                .setUserType(EnumUserType.STUDENT.getValue())
                .setUserPassword(Constant.USER_PASSWORD);
        return this.insert(user)?Constant.SUCCESS:Constant.ERROR;
    }



    /**
     * 校验账号 用户名是否重复
     * @param user
     * @return
     */
    private String checkCodeAndName(SelectUserBase user) {
        //校验重复性 code 和 name
        SelectUserBase userBase = new SelectUserBase().setUserCode(user.getUserCode());
        userBase = selectUserBaseMapper.selectOne(userBase);
        if (!ObjectUtils.isEmpty(userBase)){
            //账号重复
            logger.info("用户账号重复");
            return Constant.STU_ADD_ERROR_CODE_EXIST;
        }
//        userBase = new SelectUserBase().setUserName(user.getUserName());
//        userBase = selectUserBaseMapper.selectOne(userBase);
//        if (!ObjectUtils.isEmpty(userBase)){
//            logger.info("姓名重复");
//            return  Constant.STU_ADD_ERROR_NAME_EXIST;
//        }
        return null;
    }


    /***
     * 学生导入
     * @param request
     * @return
     */
    @Override
    public String stuUpload(HttpServletRequest request) {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile importFile = multipartRequest.getFile("fileField");
        if (importFile == null || !"select_students.xls".equals(importFile.getOriginalFilename())) {
            return Constant.STU_IMPORT_ERROR_FILE_ERROR;
        }
        // Excel的表头与文字对应，获取Excel表头
        LinkedHashMap<String, String> map = new LinkedHashMap<>(9);
        map.put("账号", "userCode");
        map.put("姓名", "userName");
        map.put("性别", "userSex");
        map.put("邮箱", "userMail");
        map.put("电话", "userPhone");
        map.put("qq", "userQq");
        map.put("专业", "stuMajorName");
        map.put("班级", "stuClass");
        map.put("届别", "stuYear");

        // 调用Excel共用类，转化成List
        List<ImportStuVo> importVOS;
        try {
            importVOS = ExcelUtil.excelToList(importFile.getInputStream(), "import", ImportStuVo.class, map, new String[]{});
        } catch (Exception e) {
            logger.info("ERROR -> 车型导入 : " + e.getMessage());
            return Constant.STU_IMPORT_ERROR_FILE_ERROR;
        }

        //导入失败记录
        List<String> importErrorList = new ArrayList<>();
        int importSuccessCount = 0;

        for (ImportStuVo importStuVo : importVOS) {
            //检查专业是否存在，不存在则不可导入
            SelectMajor selectMajor = new SelectMajor()
                    .setMajName(importStuVo.getStuMajorName());
            selectMajor = selectMajorMapper.selectOne(selectMajor);
            if (selectMajor == null) {
                importErrorList.add(importStuVo.getUserName());
                logger.info("添加失败 : " + importStuVo.toString());
                continue;
            }
            //车辆初始化
            try {
                if (!this.initStu(importStuVo)) {
                    logger.info("学生导入失败 : " + importStuVo.getUserName());
                    importErrorList.add(importStuVo.getUserName());
                    continue;
                }

            } catch (Exception e) {
                logger.info("学生导入失败 : " + importStuVo.getUserName() + " Cause :" + e.getMessage());
                importErrorList.add(importStuVo.getUserName());
                continue;

            }
            //记录成功导入车辆数
            importSuccessCount++;
            logger.info("学生导入成功 : " + importStuVo.getUserName() + " , 本次导入学生数 : " + importVOS.size() + " , 已导入学生数 : " + importSuccessCount);
        }
        logger.info("本次导入学生数 : " + importVOS.size() + " , 导入成功 : " + importSuccessCount + " , 导入失败 : " + importErrorList.size());

        return "导入成功 : " + importSuccessCount + " , 导入失败 : " + importErrorList.size() + (importErrorList.size() <= 0 ? "" : " , 失败学生姓名 : " + importErrorList.toString());
    }


    /**
     * 学生导入初始化
     * @param importStuVo
     * @return
     */
    private boolean initStu(ImportStuVo importStuVo) {
        //TODO 账号格式验证

        //查看是否重复
        SelectUserBase selectUserBase = new SelectUserBase()
                .setUserCode(importStuVo.getUserCode());
        selectUserBase = selectUserBaseMapper.selectOne(selectUserBase);
        if (!ObjectUtils.isEmpty(selectUserBase)){
            logger.info("------------》学生重复！");
            return false;
        }
        SelectUserBase userBase = new SelectUserBase()
                .setUserName(importStuVo.getUserName())
                .setUserCode(importStuVo.getUserCode())
                .setUserSex(importStuVo.getUserSex())
                .setUserMail(importStuVo.getUserMail())
                .setUserPhone(importStuVo.getUserPhone())
                .setUserQq(importStuVo.getUserQq())
                .setStuMajorName(importStuVo.getStuMajorName())
                .setStuClass(importStuVo.getStuClass())
                .setStuYear(importStuVo.getStuYear())
                .setUserPassword(Constant.USER_PASSWORD)
                .setUserType(EnumUserType.STUDENT.getValue())
                .setUserStatus(EnumEnOrDis.ENABLED.getValue())
                .setGmtCreate(new Date());
        if (!this.insert(userBase)){
            logger.info("添加失败 : " + importStuVo.getUserName());
            return false;
        }
        return true;
    }









/***************************************************** Tea ********************************************************************/
/***************************************************** Tea ********************************************************************/
/***************************************************** Tea ********************************************************************/


    /**
     * 异步生成教师列表
     * @param userBaseVo
     * @return
     */
    @Override
    public String teaListAjax(SelectUserBaseVo userBaseVo) {
        Page<SelectUserBaseDto> page = new Page<>(userBaseVo.getPage(),userBaseVo.getPageSize());
        List<SelectUserBase> userList = selectUserBaseMapper.getUserByPage(page,userBaseVo);
        List<SelectUserBaseDto> userBaseDto = SelectMapStructMapper.INSTANCE.SelectUserBasesPo2Dto(userList);
        setUserDto(userBaseDto);
        Map<String,Object> map = new HashMap<>();
        map.put("page",page);
        map.put("teaList",userBaseDto);
        String object = JSONObject.toJSONString(map);
        return object;
    }

    /***
     * 教师启用禁用
     * @param userBase
     * @return
     */
    @Override
    public String teaAble(SelectUserBase userBase) {
        //todo 有题目信息的不可禁用/系别禁用时教师不可启用
        SelectUserBase selectUserBase = selectUserBaseMapper.selectById(userBase.getId());
        if (ObjectUtils.isEmpty(selectUserBase)){
            return Constant.ERROR;
        }
        return this.updateById(userBase)?Constant.SUCCESS:Constant.ERROR;
    }

    /**
     * 教师添加、编辑初始化
     * @param modelAndView
     * @param userBaseVo
     * @return
     */
    @Override
    public ModelAndView teaInitAddAndUpdate(ModelAndView modelAndView, SelectUserBaseVo userBaseVo) {
        SelectUserBase userBase = this.selectById(userBaseVo.getId());
        if (!ObjectUtils.isEmpty(userBase)){
            SelectUserBaseDto userBaseDto = SelectMapStructMapper.INSTANCE.SelectUserBasePo2Dto(userBase);
            setUserDto(userBaseDto);
            modelAndView.addObject("user",userBaseDto);
        }
        List<SelectDepartment> teaDepList = selectDepartmentMapper.selectTeaDep();
        modelAndView.addObject("teaPosition",EnumTeaPosition.toMap());
        modelAndView.addObject("teaEducation",EnumTeaEducation.toMap());
        modelAndView.addObject("teaDepList",teaDepList);
        return modelAndView;
    }

    /***
     * 教师添加
     * @param userBase
     * @return
     */
    @Override
    public String teaAdd(SelectUserBase userBase) {
        //校验重复性
        String msg = checkCodeAndName(userBase);
        if (!StringUtils.isEmpty(msg)) return msg;
        userBase.setGmtCreate(new Date())
                .setUserType(EnumUserType.TEACHER.getValue())
                .setUserPassword(Constant.USER_PASSWORD);
        return this.insert(userBase)?Constant.SUCCESS:Constant.ERROR;
    }

    /**
     * 教师编辑
     * @param userBase
     * @return
     */
    @Override
    public String teaUpdate(SelectUserBase userBase) {
        String msg = checkCodeAndName(userBase);
        if (StringUtils.isEmpty(msg)) return msg;
        return this.updateById(userBase)?Constant.SUCCESS:Constant.ERROR;
    }

    /**
     * 删除教师
     * @param userBase
     * @return
     */
    @Override
    public String teaDelete(SelectUserBase userBase) {
        //TODO 删除所有相关的题目记录


        return this.deleteById(userBase)?Constant.SUCCESS:Constant.ERROR;
    }

    /***
     * 批量删除教师
     * @param selectedIDs
     * @return
     */
    @Override
    public String teaDeleteAll(Integer[] selectedIDs) {
        //TODO 删除所有相关的题目记录
        return this.deleteBatchIds(Arrays.asList(selectedIDs))?Constant.SUCCESS:Constant.ERROR;
    }


    /**
     * 教师导入
     * @param request
     * @return
     */
    @Override
    public String teaUpload(HttpServletRequest request) {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile importFile = multipartRequest.getFile("fileField");
        if (importFile == null || !"select_teachers.xls".equals(importFile.getOriginalFilename())) {
            return Constant.STU_IMPORT_ERROR_FILE_ERROR;
        }
        // Excel的表头与文字对应，获取Excel表头
        LinkedHashMap<String, String> map = new LinkedHashMap<>(10);
        map.put("账号", "userCode");
        map.put("姓名", "userName");
        map.put("性别", "userSex");
        map.put("邮箱", "userMail");
        map.put("电话", "userPhone");
        map.put("qq", "userQq");
        map.put("职称", "teaPosition");
        map.put("专业", "teaMajorName");
        map.put("学历", "teaEducation");
        map.put("系别", "teaDepName");

        // 调用Excel共用类，转化成List
        List<ImportTeaVo> importVOS;
        try {
            importVOS = ExcelUtil.excelToList(importFile.getInputStream(), "import", ImportTeaVo.class, map, new String[]{});
        } catch (Exception e) {
            logger.info("ERROR -> 教师导入 : " + e.getMessage());
            return Constant.STU_IMPORT_ERROR_FILE_ERROR;
        }

        //导入失败记录
        List<String> importErrorList = new ArrayList<>();
        int importSuccessCount = 0;

        for (ImportTeaVo importTeaVo : importVOS) {
            //检查系别是否存在，不存在则不可导入
            SelectDepartment selectDepartment = new SelectDepartment()
                    .setDepName(importTeaVo.getTeaDepName());
            selectDepartment = selectDepartmentMapper.selectOne(selectDepartment);
            if (selectDepartment == null) {
                importErrorList.add(importTeaVo.getUserName());
                logger.info("添加失败 : " + importTeaVo.toString());
                continue;
            }
            //车辆初始化
            try {
                if (!this.initTea(importTeaVo)) {
                    logger.info("教师导入失败 : " + importTeaVo.getUserName());
                    importErrorList.add(importTeaVo.getUserName());
                    continue;
                }

            } catch (Exception e) {
                logger.info("教师导入失败 : " + importTeaVo.getUserName() + " Cause :" + e.getMessage());
                importErrorList.add(importTeaVo.getUserName());
                continue;

            }
            //记录成功导入车辆数
            importSuccessCount++;
            logger.info("教师导入成功 : " + importTeaVo.getUserName() + " , 本次导入教师数 : " + importVOS.size() + " , 已导入教师数 : " + importSuccessCount);
        }
        logger.info("本次导入教师数 : " + importVOS.size() + " , 导入成功 : " + importSuccessCount + " , 导入失败 : " + importErrorList.size());

        return "导入成功 : " + importSuccessCount + " , 导入失败 : " + importErrorList.size() + (importErrorList.size() <= 0 ? "" : " , 失败教师姓名 : " + importErrorList.toString());
    }

    private boolean initTea(ImportTeaVo importTeaVo) {
        //TODO 格式校验

        //重复性校验
        SelectUserBase selectUserBase = new SelectUserBase()
                .setUserCode(importTeaVo.getUserCode());
        selectUserBase = selectUserBaseMapper.selectOne(selectUserBase);
        if (!ObjectUtils.isEmpty(selectUserBase)){
            logger.info("------------》教师重复！");
            return false;
        }
        SelectUserBase userBase = new SelectUserBase()
                .setUserName(importTeaVo.getUserName())
                .setUserCode(importTeaVo.getUserCode())
                .setUserSex(importTeaVo.getUserSex())
                .setUserMail(importTeaVo.getUserMail())
                .setUserPhone(importTeaVo.getUserPhone())
                .setUserQq(importTeaVo.getUserQq())
                .setTeaPosition(importTeaVo.getTeaPosition())
                .setTeaMajorName(importTeaVo.getTeaMajorName())
                .setTeaEducation(importTeaVo.getTeaEducation())
                .setTeaDepName(importTeaVo.getTeaDepName())
                .setUserPassword(Constant.USER_PASSWORD)
                .setUserType(EnumUserType.TEACHER.getValue())
                .setUserStatus(EnumEnOrDis.ENABLED.getValue())
                .setGmtCreate(new Date());
        if (!this.insert(userBase)){
            logger.info("添加失败 : " + importTeaVo.getUserName());
            return false;
        }
        return true;
    }

}
