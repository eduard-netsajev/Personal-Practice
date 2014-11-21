__author__ = 'Netšajev'

import matplotlib.pyplot as plt
import networkx as nx

not_found = True
short_path = 0
G = None
# 1 6
while not_found:
    G = nx.fast_gnp_random_graph(13, 2 * 0.1654)
    try:
        short_path = nx.shortest_path(G, 1, 6)
        not_found = False
    except nx.NetworkXNoPath:
        pass
print(short_path)

pos = nx.spring_layout(G)

if G is not None:
    nx.draw(G, pos, with_labels=True, node_color='g')

G.clear()
for i in range(len(short_path)-1):
    G.add_edge(short_path[i], short_path[i+1])

nx.draw_networkx_edges(G, pos, edge_color='red', alpha=0.5, width=3)

plt.show()
