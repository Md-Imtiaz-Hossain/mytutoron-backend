package imtiaz.sktech.mytutoron.model.pagination;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaginationArgs {

    private int pageNo;

    private int pageSize;

    private String sortBy;

    private AscOrDesc ascOrDesc;

    private Map<String, Object> parameters;
}
