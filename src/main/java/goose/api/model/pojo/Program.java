package goose.api.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author lsx
 * @since 2023-03-21
 */
public class Program implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.NONE)
    private Integer id;

    private Integer userId;

    private String pyName;

    private String labelTitle;

    private String labelQuestion;

    private String labelTask;

    private String imageCover;

    private String labelFirst;

    private String imageFirst;

    private String labelSecond;

    private String imageSecond;

    private Integer views;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPyName() {
        return pyName;
    }

    public void setPyName(String pyName) {
        this.pyName = pyName;
    }

    public String getLabelTitle() {
        return labelTitle;
    }

    public void setLabelTitle(String labelTitle) {
        this.labelTitle = labelTitle;
    }

    public String getLabelQuestion() {
        return labelQuestion;
    }

    public void setLabelQuestion(String labelQuestion) {
        this.labelQuestion = labelQuestion;
    }

    public String getLabelTask() {
        return labelTask;
    }

    public void setLabelTask(String labelTask) {
        this.labelTask = labelTask;
    }

    public String getImageCover() {
        return imageCover;
    }

    public void setImageCover(String imageCover) {
        this.imageCover = imageCover;
    }

    public String getLabelFirst() {
        return labelFirst;
    }

    public void setLabelFirst(String labelFirst) {
        this.labelFirst = labelFirst;
    }

    public String getImageFirst() {
        return imageFirst;
    }

    public void setImageFirst(String imageFirst) {
        this.imageFirst = imageFirst;
    }

    public String getLabelSecond() {
        return labelSecond;
    }

    public void setLabelSecond(String labelSecond) {
        this.labelSecond = labelSecond;
    }

    public String getImageSecond() {
        return imageSecond;
    }

    public void setImageSecond(String imageSecond) {
        this.imageSecond = imageSecond;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Program{" +
                "id=" + id +
                ", pyName='" + pyName + '\'' +
                ", labelTitle='" + labelTitle + '\'' +
                ", labelQuestion='" + labelQuestion + '\'' +
                ", labelTask='" + labelTask + '\'' +
                ", imageCover='" + imageCover + '\'' +
                ", labelFirst='" + labelFirst + '\'' +
                ", imageFirst='" + imageFirst + '\'' +
                ", labelSecond='" + labelSecond + '\'' +
                ", imageSecond='" + imageSecond + '\'' +
                ", views=" + views +
                ", createTime=" + createTime +
                '}';
    }
}
