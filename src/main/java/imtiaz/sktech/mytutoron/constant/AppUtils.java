package imtiaz.sktech.mytutoron.constant;

import imtiaz.sktech.mytutoron.model.pagination.AscOrDesc;
import imtiaz.sktech.mytutoron.model.pagination.PaginationArgs;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Map;

public final class AppUtils {

    private AppUtils() {}

    public static Pageable getPageable(PaginationArgs paginationArgs) {
        int pageNo = paginationArgs.getPageNo();
        int pageSize = paginationArgs.getPageSize();
        String sortBy = paginationArgs.getSortBy();
        AscOrDesc ascOrDesc = paginationArgs.getAscOrDesc();

        if (sortBy != null && !sortBy.isBlank()) {
            Sort sort;
            if (ascOrDesc.equals(AscOrDesc.ASC)) {
                sort = Sort.by(sortBy).ascending();
            }
            else {
                sort = Sort.by(sortBy).descending();
            }

            return PageRequest.of(pageNo, pageSize, sort);
        }
        return PageRequest.of(pageNo, pageSize);
    }

    public static Map<String, Object> getSpecificParameters(Map<String, Object> parameters) {
        parameters.remove(AppConstant.PAGE_NO);
        parameters.remove(AppConstant.PAGE_SIZE);
        parameters.remove(AppConstant.SORT_BY);
        parameters.remove(AppConstant.ASC_OR_DESC);
        parameters.remove(AppConstant.PARAMETERS);
        return parameters;
    }
}
