package nivida.com.amulyamica;

import java.io.Serializable;

/**
 * Created by SEO on 10/27/2016.
 */

public class Bean_LaminateList implements Serializable {
    String laminate_id;
    String finishTypeId;
    String laminateTypeId;
    String laminateName;
    String designName;
    String designNo;
    String description;
    String technicalSpecification;
    String size;
    String laminateImage;
    String sortOrder;
    String status;
    String categoryName;
    String finishTypeName;
    int totalPageCount;
    boolean favourite_laminate=true;

    public Bean_LaminateList() {
    }

    public Bean_LaminateList(String laminate_id, String finishTypeId, String laminateTypeId, String laminateName, String designName, String designNo, String description, String technicalSpecification, String size, String laminateImage, String sortOrder, String status, String categoryName, String finishTypeName, int totalPageCount, boolean favourite_laminate) {
        this.laminate_id = laminate_id;
        this.finishTypeId = finishTypeId;
        this.laminateTypeId = laminateTypeId;
        this.laminateName = laminateName;
        this.designName = designName;
        this.designNo = designNo;
        this.description = description;
        this.technicalSpecification = technicalSpecification;
        this.size = size;
        this.laminateImage = laminateImage;
        this.sortOrder = sortOrder;
        this.status = status;
        this.categoryName = categoryName;
        this.finishTypeName = finishTypeName;
        this.totalPageCount = totalPageCount;
        this.favourite_laminate = favourite_laminate;
    }

    public String getLaminate_id() {
        return laminate_id;
    }

    public void setLaminate_id(String laminate_id) {
        this.laminate_id = laminate_id;
    }

    public String getFinishTypeId() {
        return finishTypeId;
    }

    public void setFinishTypeId(String finishTypeId) {
        this.finishTypeId = finishTypeId;
    }

    public String getLaminateTypeId() {
        return laminateTypeId;
    }

    public void setLaminateTypeId(String laminateTypeId) {
        this.laminateTypeId = laminateTypeId;
    }

    public String getLaminateName() {
        return laminateName;
    }

    public void setLaminateName(String laminateName) {
        this.laminateName = laminateName;
    }

    public String getDesignName() {
        return designName;
    }

    public void setDesignName(String designName) {
        this.designName = designName;
    }

    public String getDesignNo() {
        return designNo;
    }

    public void setDesignNo(String designNo) {
        this.designNo = designNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTechnicalSpecification() {
        return technicalSpecification;
    }

    public void setTechnicalSpecification(String technicalSpecification) {
        this.technicalSpecification = technicalSpecification;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getLaminateImage() {
        return laminateImage;
    }

    public void setLaminateImage(String laminateImage) {
        this.laminateImage = laminateImage;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getFinishTypeName() {
        return finishTypeName;
    }

    public void setFinishTypeName(String finishTypeName) {
        this.finishTypeName = finishTypeName;
    }

    public int getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(int totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    public boolean isFavourite_laminate() {
        return favourite_laminate;
    }

    public void setFavourite_laminate(boolean favourite_laminate) {
        this.favourite_laminate = favourite_laminate;
    }
}
