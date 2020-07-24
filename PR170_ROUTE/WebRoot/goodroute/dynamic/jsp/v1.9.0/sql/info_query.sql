

-- 1. P101_TurnInfo.jsp
select  turn$id             turn_id,
        turn#node$id        turn_node_id,
        turn_st#link$id     turn_st_link_id,
        turn_ed#link$id     turn_ed_link_id,
        turn#turntype$cd    turn_turntype_cd,
        turn#turnuse$cd     turn_turnuse_cd,
        turn#opertime$cd    turn_opertime_cd,
        turn_remark
from    tr002_turn
        where turn_st#link$id = ${ param.st_link_id }
        and   turn_ed#link$id = ${ param.ed_link_id }
        
-- sample        
select * from tr002_turn
        where turn_st#link$id = 1170004300
        and   turn_ed#link$id = 1170004400


-- 2. P102_LinkInfo.jsp
select 	link$id                     link_id,
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
from tr003_link
        where link$id = ${ param.link_id }
-- sample
select * from tr003_link
        where link$id = 1230021200


-- 3. P103_NodeInfo.jsp
select 	node$id             node_id,
        node#nodetype$cd    node_nodetype_cd,
        node_name,
        node#turnp$cd       node_turnp_cd,
        node_remark,
        node#nodesign$cd    node_nodesign_cd,
        node_gx,
        node_gy,
        node_tx,
        node_ty 
from tr001_node
        where node$id = ${ param.node_id }
-- sample
select * from tr001_node
        where node$id = 1070005100
        
        
-- 4. P104_RoadInfo.jsp
select 	road$id             road_id,
        road#roadrank$cd    road_roadrank_cd,
        road_no,
        road_name,
        road_name_02 
from tr004_road
        where road$id = ${ param.road_id }
        
-- sample
select * from tr004_road
        where road$id = 347   