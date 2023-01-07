package com.zee.club.data.model;

import java.util.List;

public class ProductInfoMo {
    private String skuId;
    private String spuId;
    private String productTitle;
    private List<String> images;
    private String productPrice;
    private String softwareCode;
    private int heat;
    private String slogan;
    private String createTime;
    private String updateTime;
    private ExtendInfo extendInfo;

    private String productImg;
    private String productDesc;
    private String simplerIntroduce;


    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getSpuId() {
        return spuId;
    }

    public void setSpuId(String spuId) {
        this.spuId = spuId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getSoftwareCode() {
        return softwareCode;
    }

    public void setSoftwareCode(String softwareCode) {
        this.softwareCode = softwareCode;
    }

    public int getHeat() {
        return heat;
    }

    public void setHeat(int heat) {
        this.heat = heat;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public ExtendInfo getExtendInfo() {
        return extendInfo;
    }

    public void setExtendInfo(ExtendInfo extendInfo) {
        this.extendInfo = extendInfo;
    }

    public String getProductImg(){
        if(productImg != null && !productImg.trim().isEmpty()){
            return productImg;
        }else {
            if (images == null || images.size() == 0)
                return "";
            else
                return images.get(0);
        }
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getSimplerIntroduce() {
        return simplerIntroduce;
    }

    public void setSimplerIntroduce(String simplerIntroduce) {
        this.simplerIntroduce = simplerIntroduce;
    }

    public static class ExtendInfo{
        private List<String> bannerImages;

        public List<String> getBannerImages() {
            return bannerImages;
        }

        public void setBannerImages(List<String> bannerImages) {
            this.bannerImages = bannerImages;
        }
    }
}

