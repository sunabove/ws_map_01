
--select 
--rownum rno,
--a.* 
--from (
--    select  link$id,
--            road#roadrank$cd,
--            roadrank_desc,
--            road$id,
--            road_no,
--            road_name,
--            fn_node_gx,
--            fn_node_gy,
--            tn_node_gx,
--            tn_node_gy
--    from    tr902_link lv,
--            cd010_roadrank
--    where   lv.road#roadrank$cd = roadrank$cd
--) a
--order by road#roadrank$cd




select 
rownum rno,
a.* 
from (
    select  --count(*) over cnt
            lv.link$id
            ,road#roadrank$cd
            ,roadrank_desc
            ,road$id
            ,road_no
            ,road_name
            ,link_f#node$id
            ,link_t#node$id
            ,linkuse$cd
            ,linkuse_desc
            ,link_lane_no
            ,ramptype$cd
            ,ramptype_desc
            ,lv.link_spd_max_kmph
            ,l.link_rest_01#vhcltype$cd
            ,vhcltype_desc
            --,l.link_rest_02#vhcltype$cd
            --,vhcltype_desc
            --,l.link_rest_03#vhcltype$cd
            --,vhcltype_desc
            ,link_rest_w_kg
            ,link_rest_h_m
            ,fn_node_gx
            ,fn_node_gy
            ,tn_node_gx
            ,tn_node_gy
            ,ls.linkshp_wkt linkshp_geom
    from    tr902_link lv
            ,tr003_link l
            ,tr007_linkshp ls
            ,cd010_roadrank
            ,cd006_linkuse
            ,cd008_ramptype
            ,cd009_vhcltype     
    where   1=1
    and     l.link$id = lv.link$id
    and     l.link$id = ls.linkshp#link$id
    and     l.link#linkuse$cd = linkuse$cd
    and     l.link#ramptype$cd = ramptype$cd   
    and     lv.road#roadrank$cd = roadrank$cd
    and     l.link_rest_01#vhcltype$cd = vhcltype$cd
    and     l.link_rest_02#vhcltype$cd = vhcltype$cd
    and     l.link_rest_03#vhcltype$cd = vhcltype$cd
    
) a
order by    road#roadrank$cd,
            road_no
