package com.edgar.core.mvc;

import com.edgar.core.mvc.ToQueryExample.QueryType;
import com.edgar.core.repository.QueryExample;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.*;

/**
 * 根据request参数创建QueryExample.
 *
 * @author Edgar
 */
public class QueryExampleResolver implements WebArgumentResolver {

    private static final Set<String> notToExampleParam = new HashSet<String>();
    private static final String SORT = "sort";
    private static final String FIELDS = "fields";
    private static final String DESC = "-";
    private static final String QUERY_SEPARATOR = ",";
    private static final String LESS_THAN = "_lt";
    private static final String GREATER_THAN = "_gt";
    private static final String GREATER_THAN_AND_EQUAL = "_gteq";
    private static final String LESS_THAN_AND_EQUAL = "_lteq";
    private static final String NOT_EQUAL = "_neq";
    private static final String BEGIN_WITH = "_bw";
    private static final String NOT_BEGIN_WITH = "_bn";
    private static final String END_WITH = "_ew";
    private static final String NOT_END_WITH = "_en";
    private static final String CONTAIN = "_cn";
    private static final String NOT_CONTAIN = "_nc";
    private static final String IN = "_in";
    private static final String NOT_IN = "_ni";

    private static final String LIMIT = "limit";
    private static final String OFFSET = "offset";

    static {
        notToExampleParam.add("page");
        notToExampleParam.add("pageSize");
        notToExampleParam.add("timestamp");
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, NativeWebRequest webRequest)
            throws Exception {
        Class<?> paramType = methodParameter.getParameterType();
        ToQueryExample toQueryExample = methodParameter
                .getParameterAnnotation(ToQueryExample.class);
        if (paramType == QueryExample.class && toQueryExample != null) {
            Iterator<String> paramterNames = webRequest.getParameterNames();
            QueryExample example = QueryExample.newInstance();
            example.setMaxNumOfRecords(toQueryExample.maxNumOfRecords());
            while (paramterNames.hasNext()) {
                String paramName = paramterNames.next();
                String value = webRequest.getParameter(paramName);
                if (StringUtils.isBlank(paramName)) {
                    continue;
                }
                if (StringUtils.isBlank(value)) {
                    continue;
                }
                if (notToExampleParam.contains(paramName)) {
                    continue;
                }
                if (LIMIT.equals(paramName)) {
                    resolveLimit(toQueryExample, example, value);
                    continue;
                }
                if (OFFSET.equals(paramName)) {
                    resolveOffset(toQueryExample, example, value);
                    continue;
                }
                Validate.isTrue(example.getLimit() * example.getOffset() <= toQueryExample
                        .maxNumOfRecords(), "limit * offset不能大于规定的大小："
                        + toQueryExample.maxNumOfRecords());
                String[] values = StringUtils.split(value, QUERY_SEPARATOR);
                if (SORT.equals(paramName)) {
                    resolveSort(example, values);
                    continue;
                }
                if (FIELDS.equals(paramName)) {
                    resolveFields(example, values);
                    continue;
                }
                if (paramName.endsWith(LESS_THAN)) {
                    String field = StringUtils.substringBeforeLast(paramName,
                            LESS_THAN);
                    for (String v : values) {
                        example.lessThan(field, v);
                    }
                } else if (paramName.endsWith(GREATER_THAN)) {
                    String field = StringUtils.substringBeforeLast(paramName,
                            GREATER_THAN);
                    for (String v : values) {
                        example.greaterThan(field, v);
                    }
                } else if (paramName.endsWith(GREATER_THAN_AND_EQUAL)) {
                    String field = StringUtils.substringBeforeLast(paramName,
                            GREATER_THAN_AND_EQUAL);
                    for (String v : values) {
                        example.greaterThanOrEqualTo(field, v);
                    }
                } else if (paramName.endsWith(LESS_THAN_AND_EQUAL)) {
                    String field = StringUtils.substringBeforeLast(paramName,
                            LESS_THAN_AND_EQUAL);
                    for (String v : values) {
                        example.lessThanOrEqualTo(field, v);
                    }
                } else if (paramName.endsWith(NOT_EQUAL)) {
                    String field = StringUtils.substringBeforeLast(paramName,
                            NOT_EQUAL);
                    for (String v : values) {
                        example.notEqualsTo(field, v);
                    }
                } else if (paramName.endsWith(BEGIN_WITH)) {
                    String field = StringUtils.substringBeforeLast(paramName,
                            BEGIN_WITH);
                    for (String v : values) {
                        example.beginWtih(field, v);
                    }
                } else if (paramName.endsWith(NOT_BEGIN_WITH)) {
                    String field = StringUtils.substringBeforeLast(paramName,
                            NOT_BEGIN_WITH);
                    for (String v : values) {
                        example.notBeginWith(field, v);
                    }
                } else if (paramName.endsWith(END_WITH)) {
                    String field = StringUtils.substringBeforeLast(paramName,
                            END_WITH);
                    for (String v : values) {
                        example.endWtih(field, v);
                    }
                } else if (paramName.endsWith(NOT_END_WITH)) {
                    String field = StringUtils.substringBeforeLast(paramName,
                            NOT_END_WITH);
                    for (String v : values) {
                        example.notEndWith(field, v);
                    }
                } else if (paramName.endsWith(CONTAIN)) {
                    String field = StringUtils.substringBeforeLast(paramName,
                            CONTAIN);
                    for (String v : values) {
                        example.contain(field, v);
                    }
                } else if (paramName.endsWith(NOT_CONTAIN)) {
                    String field = StringUtils.substringBeforeLast(paramName,
                            NOT_CONTAIN);
                    for (String v : values) {
                        example.notContain(field, v);
                    }
                } else if (paramName.endsWith(IN)) {
                    String field = StringUtils.substringBeforeLast(paramName,
                            IN);
                    List<Object> inList = new ArrayList<Object>();
                    Collections.addAll(inList, values);
                    example.in(field, inList);
                } else if (paramName.endsWith(NOT_IN)) {
                    String field = StringUtils.substringBeforeLast(paramName,
                            NOT_IN);
                    List<Object> inList = new ArrayList<Object>();
                    Collections.addAll(inList, values);
                    example.notIn(field, inList);
                } else {
                    for (String v : values) {
                        QueryType queryType = toQueryExample.value();
                        switch (queryType) {
                            case EQUALS_TO:
                                example.equalsTo(paramName, v);
                                break;
                            case CONTAIN:
                                example.contain(paramName, v);
                                break;
                            case BEGIN_WITH:
                                example.beginWtih(paramName, v);
                                break;
                            default:
                                example.equalsTo(paramName, v);
                                break;
                        }

                    }
                }
            }
            return example;
        }
        return UNRESOLVED;
    }

    private void resolveFields(QueryExample example, String[] values) {
        List<String> valueList = Arrays.asList(values);
        for (String v : valueList) {
            example.addField(StringUtils.trim(v));
        }
    }

    private void resolveSort(QueryExample example, String[] values) {
        for (String v : values) {
            v = StringUtils.trim(v);
            if (StringUtils.startsWith(v, DESC)) {
                example.desc(StringUtils.substringAfter(v, DESC));
            } else {
                example.asc(v);
            }
        }
    }

    private void resolveOffset(ToQueryExample toQueryExample, QueryExample example, String value) {
        Integer offset = NumberUtils.toInt(value);
        Validate.isTrue(offset > 0, "offset必须是正整数值");
        Validate.isTrue(offset < toQueryExample.maxNumOfRecords(), "offset不能大于规定的大小："
                + toQueryExample.maxNumOfRecords());
        example.offset(offset);
    }

    private void resolveLimit(ToQueryExample toQueryExample, QueryExample example, String value) {
        Integer limit = NumberUtils.toInt(value);
        Validate.isTrue(limit > 0, "limit必须是正整数值");
        if (toQueryExample.maxNumOfRecords() > 0) {
            Validate.isTrue(limit < toQueryExample.maxNumOfRecords(), "limit不能大于规定的大小："
                    + toQueryExample.maxNumOfRecords());

        }
        example.limit(limit);
    }

}
