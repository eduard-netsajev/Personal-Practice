__author__ = 'Netšajev'

import matplotlib.pyplot as plt
import networkx as nx

G = nx.Graph()
# explicitly set positions
pos = {10: (0, 1.4),
       3: (-1.9, 0),
       1: (-0.3, -1),
       6: (3.2, 0.7),
       5: (-2.4, 1.3),
       4: (-3.4, 0.4),
       2: (-2.2, -1),
       7: (1.9, -0.9),
       8: (1.8, 1.4),
       9: (2.1, 0),
       0: (0, 0.2)}

G.add_edges_from([(2, 4), (4, 5), (10, 0), (1, 0), (8, 9), (2, 1), (5, 10),
                  (3, 4), (10, 9), (3, 0), (8, 6), (6, 9), (1, 7), (7, 9)])

labels = {1: r'$1$', 3: r'$3$', 10: r'$10$', 6: r'$6$', 5: r'$5$', 4: r'$4$',
          0: r'$0$', 2: r'$2$', 7: r'$7$', 8: r'$8$', 9: r'$9$'}

nx.draw_networkx_labels(G, pos, labels, font_size=16)
nx.draw_networkx_nodes(G, pos, node_size=1500, nodelist=[6, 4])
nx.draw_networkx_nodes(G, pos, node_size=1000, nodelist=[0, 1, 2, 3, 5, 7, 8, 9, 10], node_color='g')
nx.draw_networkx_edges(G, pos, alpha=0.5, width=1)


short_path = nx.shortest_path(G, 6, 4)
G.clear()
for i in range(len(short_path)-1):
    G.add_edge(short_path[i], short_path[i+1])
nx.draw_networkx_edges(G, pos, edge_color='red', alpha=0.5, width=3)


plt.axis('off')
plt.savefig("4 to 6 graph.png")
plt.show()
