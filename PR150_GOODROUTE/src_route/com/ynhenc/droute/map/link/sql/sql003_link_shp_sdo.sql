select
  LINKSHP#LINK$ID ,
  LINKSHP_GEOM ,
  '' a
from tr007_linkshp
where linkshp#link$id = &link_id 