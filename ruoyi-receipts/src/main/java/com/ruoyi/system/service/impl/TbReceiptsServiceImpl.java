package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateFormatUtil;
import com.ruoyi.common.utils.NumberFormatUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.ehcache.util.EhCacheUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.TbReceiptsMapper;
import com.ruoyi.system.domain.TbReceipts;
import com.ruoyi.system.service.ITbReceiptsService;
import com.ruoyi.common.core.text.Convert;

/**
 * 收据管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-03-17
 */
@Service
public class TbReceiptsServiceImpl implements ITbReceiptsService 
{
    @Autowired
    private TbReceiptsMapper tbReceiptsMapper;

    @Autowired
    private CacheManager cacheManager;

    /**
     * 查询收据管理
     * 
     * @param id 收据管理ID
     * @return 收据管理
     */
    @Override
    public TbReceipts selectTbReceiptsById(Long id)
    {
        return tbReceiptsMapper.selectTbReceiptsById(id);
    }

    /**
     * 查询收据管理列表
     * 
     * @param tbReceipts 收据管理
     * @return 收据管理
     */
    @Override
    public List<TbReceipts> selectTbReceiptsList(TbReceipts tbReceipts)
    {
        return tbReceiptsMapper.selectTbReceiptsList(tbReceipts);
    }

    /**
     * 新增收据管理
     * 
     * @param tbReceipts 收据管理
     * @return 结果
     */
    @Override
    public int insertTbReceipts(TbReceipts tbReceipts)
    {
        return tbReceiptsMapper.insertTbReceipts(tbReceipts);
    }

    /**
     * 修改收据管理
     * 
     * @param tbReceipts 收据管理
     * @return 结果
     */
    @Override
    public int updateTbReceipts(TbReceipts tbReceipts)
    {
        return tbReceiptsMapper.updateTbReceipts(tbReceipts);
    }

    /**
     * 删除收据管理对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTbReceiptsByIds(String ids)
    {
        return tbReceiptsMapper.deleteTbReceiptsByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除收据管理信息
     * 
     * @param id 收据管理ID
     * @return 结果
     */
    @Override
    public int deleteTbReceiptsById(Long id)
    {
        return tbReceiptsMapper.deleteTbReceiptsById(id);
    }

    @Override
    public String initPrintData(String tempContent, String receiptId) {
        TbReceipts receipts = tbReceiptsMapper.selectTbReceiptsById(Long.parseLong(receiptId));
        String operator = (String) EhCacheUtils.getUserInfo(receipts.getOperator());
        String receiptMode = (String) EhCacheUtils.getDictInfo("receipt_type", receipts.getReceiptMode());//收款方式
        String remarkType = (String) EhCacheUtils.getDictInfo("receipt_remark_type", receipts.getRemarkType());//备注事项
        tempContent = tempContent.replace("${companyName}",receipts.getCompanyName());
        tempContent = tempContent.replace("${receiptNo}", StringUtils.isEmpty(receipts.getReceiptNo())?"":receipts.getReceiptNo());
        tempContent = tempContent.replace("${rmb}", NumberFormatUtil.format2(receipts.getReceiptRmb()));
        tempContent = tempContent.replace("${rmbUpper}", receipts.getReceiptRmbUpp());
        tempContent = tempContent.replace("${remarkType}", remarkType);
        tempContent = tempContent.replace("${remark}", receipts.getRemark());
        tempContent = tempContent.replace("${receiptMode}", receiptMode);
        tempContent = tempContent.replace("${operator}", operator);
        tempContent = tempContent.replace("${cashier}", StringUtils.isEmpty(receipts.getCashier())?"":receipts.getCashier());
        tempContent = tempContent.replace("${receiptDate}", DateFormatUtil.format(receipts.getReceiptDate(), DateFormatUtil.FORMAT_DATE_STR));
        return tempContent;
    }
}