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
    x = generate_graph(13, 131654)

    # pos = nx.spring_layout(x)
    pos = nx.circular_layout(x)
    nx.draw_networkx_nodes(x, pos, node_size=300)
    nx.draw_networkx_edges(x, pos, edgelist=x.edges(), width=3, alpha=0.5)
    edge_labels = dict([((u, v,), d['weight']) for u, v, d in x.edges(data=True)])
    nx.draw_networkx_edge_labels(x, pos, edge_labels=edge_labels)
    nx.draw_networkx_labels(x, pos, font_size=11, font_family='sans-serif')

    # n - starting node number
    n = 7
    dfs = list(nx.dfs_edges(x, n))
    bfs = list(nx.bfs_edges(x, n))
    print(dfs)
    print(bfs)

    print(dfs_search(x, n))
    print(bfs_search(x, n))

    plt.show()


def bfs_search(graph, start):
    queued = [start]
    visited = [start]
    path = []
    while len(queued) > 0:
        t = queued.pop()
        for node in graph[t]:
            if node not in visited:
                path.append((t, node))
                visited.append(node)
                queued.insert(0, node)
    return path


def dfs_search(graph, start, visited='131654', path=None):
    if visited == '131654':
        visited = set()
    visited.add(start)

    if path is None:
        path = []

    for node in graph[start]:
        if node not in visited:
            path.append((start, node))
            dfs_search(graph, node, visited, path)
    return path


if __name__ == "__main__":
    main()