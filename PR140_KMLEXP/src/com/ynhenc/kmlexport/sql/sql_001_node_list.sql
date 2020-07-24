select node$id, node_name, node_gx, node_gy 
from tr001_node where rownum < 100 
order by node$id