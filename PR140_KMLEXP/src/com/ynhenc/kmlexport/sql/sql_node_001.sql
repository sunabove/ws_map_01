select 
--'' a,
--count(*) over () cnt,
rownum rno,
a.* 
from(
    select road#roadrank$cd,
           roadrank_desc, 
           road_no, 
           road_name,
           node#nodetype$cd,
           nodetype_desc,
           node#turnp$cd,
           turnp_desc,
           node#nodesign$cd, 
           nodesign_desc, 
           node$id, 
           node_name, 
           node_gx, 
           node_gy, 
           link_ft#node$id 
    from   tr001_node n, 
           tr901_linknoderel l, 
           tr004_road r, 
           cd010_roadrank, 
           cd001_nodetype,
           cd003_nodesign,
           cd002_turnp
    where  n.NODE$ID =+ l.LINK_Ft#NODE$ID
    and    l.LINK#ROAD$ID =+ r.ROAD$ID
    and    r.ROAD#ROADRANK$CD = roadrank$cd
    and    node#nodetype$cd = nodetype$cd 
    and    node#nodesign$cd = nodesign$cd
    and    node#turnp$cd = turnp$cd
    --and    rownum < 100 
    order by  roadrank$cd, 
              road_no, 
              node#nodetype$cd, 
              node$id, 
              node_name
) a


