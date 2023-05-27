package goose.api.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;


/**
 * <p>
 *
 * </p>
 *
 * @author lsx
 * @since 2023-03-21
 */
public class Options implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.NONE)
    private Integer id;

    private Integer pyId;

    private String optionName;

    private String optionText;

    private String optionOptions;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPyId() {
        return pyId;
    }

    public void setPyId(Integer pyId) {
        this.pyId = pyId;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public String getOptionOptions() {
        return optionOptions;
    }

    public void setOptionOptions(String optionOptions) {
        this.optionOptions = optionOptions;
    }

    @Override
    public String toString() {
        return "Options{" +
                "id=" + id +
                ", pyId=" + pyId +
                ", optionName='" + optionName + '\'' +
                ", optionText='" + optionText + '\'' +
                ", optionOptions='" + optionOptions + '\'' +
                '}';
    }
}
