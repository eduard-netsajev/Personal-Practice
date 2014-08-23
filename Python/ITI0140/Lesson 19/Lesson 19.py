__author__ = 'Netšajev'

import networkx as nx
import matplotlib.pyplot as plt
import random


def generate_graph(num_nodes, seed):
        """
        Generates a connected graph using the seed

        Args:
        seed -- your student ID

        Returns:
        networkx graph
        """
        random.seed(seed)
        g = nx.Graph()
        g.add_nodes_from(range(1, num_nodes))
        for i in range(num_nodes):
            g.add_edges_from([(i, random.randrange(1, num_nodes), {'weight': random.randrange(1, 20)}),
                              (i, random.randrange(1, num_nodes), {'weight': random.randrange(1, 20)})])
        return g

def main():
    x = generate_graph(9, 131654)

    #pos = nx.spring_layout(x)
    pos = nx.circular_layout(x)
    nx.draw_networkx_nodes(x, pos, node_size=300)
    nx.draw_networkx_edges(x, pos, edgelist=x.edges(), width=3, alpha=0.5)
    edge_labels=dict([((u,v,),d['weight']) for u, v, d in x.edges(data=True)])
    nx.draw_networkx_edge_labels(x,pos,edge_labels=edge_labels)
    nx.draw_networkx_labels(x, pos, font_size=11, font_family='sans-serif')

    # n - starting node number
    n = 1
    dfs = list(nx.dfs_edges(x, n))
    bfs = list(nx.bfs_edges(x, n))
    print(dfs)
    print(bfs)

    dfs_search(x, n)
    plt.show()


def dfs_search(graph, start):
    stack = [start]
    visited = []
    while len(stack) > 0:
        node = stack.pop()
        if node not in visited:
            visited.append(node)
            for next_node in list(graph[node]):
                #BDS stack.insert(0, next_node)
                stack.append(next_node)

    print(visited)

if __name__ == "__main__":
    main()