<%@page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:if test="${ param.link_id != null }" >
    <c:set var="sql" scope="request" >  
        select  link$id                     link_id,
		        link_f#node$id              link_f_node_id,
		        link_t#node$id              link_t_node_id,
		        link#linkuse$cd             link_linkuse_cd,
		        link_lane_no,
		        link#road$id                link_road_id,
		        link#multilink$cd           link_multilink_cd,
		        link#ramptype$cd            link_ramptype_cd,
		        link_spd_max_kmph,
		        link_rest_01#vhcltype$cd    link_rest_01_vhcltype_cd,
		        link_rest_02#vhcltype$cd    link_rest_02_vhcltype_cd,
		        link_rest_03#vhcltype$cd    link_rest_03_vhcltype_cd,
		        link_rest_w_kg,
		        link_rest_h_m,
		        link_remark,
		        link_var_lane,
		        link_bus_lane,
		        link_length_m,
		        link_roadname_loc,
		        link#linktype$cd            link_linktype_cd,
		        link_idx
		from	tr003_link
		where	link$id = ${ param.link_id }        
    </c:set>
</c:if> 

<jsp:include flush="true" page="../com/Com_DataSet.jsp" >
	<jsp:param name="sql" value="${ sql }" />
	<jsp:param name="noOfRowsPerPage" value="-1" />
	<jsp:param name="sqlParamList" value="${ sqlParamList }"  />
</jsp:include>
	 