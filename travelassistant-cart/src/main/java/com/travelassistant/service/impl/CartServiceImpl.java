package com.travelassistant.service.impl;

import com.travelassistant.mapper.CartMapper;
import com.travelassistant.service.CartService;
import com.travelassistant.clients.ProductClient;
import com.travelassistant.param.CartParam;
import com.travelassistant.param.ProductIdsParam;
import com.travelassistant.pojo.Cart;
import com.travelassistant.pojo.Product;
import com.travelassistant.utils.R;
import com.travelassistant.vo.CartVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * projectName: travelassistant
 *
 * @author: 邱绍峰
 * time: 2024/03/20 22:00 周四
 * description:
 */
@Service
@Slf4j
public class CartServiceImpl extends ServiceImpl<CartMapper,Cart> implements CartService {

    @Autowired
    private ProductClient productClient;

    @Autowired
    private CartMapper cartMapper;

    /**
     * 添加购物车
     *
     * @param cartParam
     * @return
     */
    @Override
    public R save(CartParam cartParam) {

        //查询关联的商品信息 复用商品集合查询
        List<Integer> ids = new ArrayList<>();
        ids.add(cartParam.getProductId());
        ProductIdsParam productIdsParam = new ProductIdsParam();
        productIdsParam.setProductIds(ids);
        List<Product> productList = productClient.ids(productIdsParam);

        if (productList == null || productList.size() == 0){
            log.info("CartServiceImpl.save业务开始，商品被移除,无法添加!");
            return R.fail("商品已经被删除,无法添加!");
        }
        //1.检查是否已经达到最大库存
        Product product = productList.get(0);
        int productNum = product.getProductNum();
        if (productNum == 0){
            R fail = R.fail("已经没有库存,无法购买!");
            fail.setCode("003"); //没有库存的错误码
            return fail;
        }
        //2.检查是不是第一次添加
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",cartParam.getUserId());
        queryWrapper.eq("product_id",cartParam.getProductId());
        Cart cart = cartMapper.selectOne(queryWrapper);
        if (cart != null){
            //不是第一次,直接返回已经添加过即可!
            //更新属性 + 1
            cart.setNum(cart.getNum()+1);
            cartMapper.updateById(cart);
            R ok = R.ok("商品已经在购物车,数量+1!");
            ok.setCode("002");
            return ok;
        }
        //3.第一次结果封装
        cart = new Cart();
        cart.setNum(1);
        cart.setProductId(cartParam.getProductId());
        cart.setUserId(cartParam.getUserId());

        cartMapper.insert(cart);

        //结果封装
        CartVo cartVo = new CartVo(product,cart);
        log.info("CartServiceImpl.save业务结束，结果:{}",cartVo);
        return R.ok(cartVo);
    }

    /**
     * 查询购物车数据集合
     *
     * @param cartParam
     * @return
     */
    @Override
    public R list(CartParam cartParam) {
        //获取用户id
        Integer userId = cartParam.getUserId();
        //查询用户id对应的购物车数据
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        List<Cart> cartList = cartMapper.selectList(queryWrapper);
        if (cartList == null || cartList.size() == 0){
            return R.ok("购物车没有数据!",cartList);
        }
        //封装商品集合,查询商品数据
        List<Integer> ids = new ArrayList<>();
        for (Cart cart : cartList) {
            ids.add(cart.getProductId());
        }
        
        ProductIdsParam productIdsParam = new ProductIdsParam();
        productIdsParam.setProductIds(ids);

        List<Product> productList = productClient.ids(productIdsParam);
        //集合转map!
        Map<Integer, Product> map = productList.stream().collect(Collectors.
                toMap(Product::getProductId, v -> v));
        System.out.println("map = " + map);
        //结果封装即可
        List<CartVo> list = new ArrayList<>(cartList.size());
        for (Cart cart : cartList) {
            CartVo cartVo = new CartVo(map.get(cart.getProductId()),cart);
            list.add(cartVo);
        }

        R ok = R.ok(list);
        log.info("CartServiceImpl.list业务结束，结果:{}",ok);
        return ok;
    }

    /**
     * 修改购物车数量
     *
     * @param cartParam
     * @return
     */
    @Override
    public R update(CartParam cartParam) {

        //1.查询商品对应的详情
        List<Integer> ids = new ArrayList<>();
        ids.add(cartParam.getProductId());
        ProductIdsParam productIdsParam = new ProductIdsParam();
        productIdsParam.setProductIds(ids);
        List<Product> productList = productClient.ids(productIdsParam);

        if (productList == null || productList.size() == 0){
            log.info("CartServiceImpl.update业务开始，商品被移除,无法添加!");
            return R.fail("商品已经被删除,无法添加!");
        }
        //1.检查是否已经达到最大库存
        Product product = productList.get(0);
        int productNum = product.getProductNum();
        //2.对比是否购买数量是够超出库存
        if (cartParam.getNum() > productNum){
            R fail = R.fail("修改失败,超出库存数量!");
            log.info("CartServiceImpl.update业务结束，结果:{}",fail);
            return fail;
        }

        //3.数据修改
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",cartParam.getUserId());
        queryWrapper.eq("product_id",cartParam.getProductId());
        Cart cart = cartMapper.selectOne(queryWrapper);

        cart.setNum(cartParam.getNum());

        cartMapper.updateById(cart);
        //4.结果封装
        R ok = R.ok("购物车数量更新成功!");
        log.info("CartServiceImpl.update业务结束，结果:{}",ok);
        return ok;
    }

    /**
     * 移除购物车数据
     *
     * @param cartParam
     * @return
     */
    @Override
    public R remove(CartParam cartParam) {

        //删除参数封装
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",cartParam.getUserId());
        queryWrapper.eq("product_id",cartParam.getProductId());
        //删除数据
        cartMapper.delete(queryWrapper);
        return R.ok("删除数据成功!");
    }

    /**
     * 检查商品是否存在
     *
     * @param productId
     * @return
     */
    @Override
    public R check(Integer productId) {

        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_id",productId);
        Long total = cartMapper.selectCount(queryWrapper);

        if (total == 0L){
            return R.ok("购物车中不存在要删除的商品!");
        }

        return R.fail("购物车中存在要删除的商品!");
    }
}
