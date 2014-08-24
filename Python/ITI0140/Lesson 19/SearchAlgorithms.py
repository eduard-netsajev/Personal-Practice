__author__ = 'Netšajev'

import networkx as nx
import matplotlib.pyplot as plt
import numpy as np
import random
import time


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


def find_with_bfs(graph_size, edge_probability):

    x = nx.fast_gnp_random_graph(graph_size, edge_probability, '131654')
    n = min(graph_size, 100)
    m = 100 // n
    start_time = time.time()

    for d in range(m):
        for i in range(n):
            bfs_search(x, i)

    end_time = time.time()
    return end_time - start_time


def find_with_dfs(graph_size, edge_probability):

    x = nx.fast_gnp_random_graph(graph_size, edge_probability, '131654')
    n = min(graph_size, 100)
    m = 100 // n
    start_time = time.time()

    for d in range(m):
        for i in range(n):
            dfs_search(x, i)

    end_time = time.time()
    return end_time - start_time


def find_with_nx(graph_size, edge_probability, mode='dfs'):
    x = nx.fast_gnp_random_graph(graph_size, edge_probability, '131654')
    n = min(graph_size, 100)
    m = 100 // n
    if mode == 'dfs':
        start_time = time.time()
        for d in range(m):
            for i in range(n):
                list(nx.dfs_edges(x, i))

        end_time = time.time()
    elif mode == 'bfs':
        start_time = time.time()
        for d in range(m):
            for i in range(n):
                list(nx.dfs_edges(x, i))

        end_time = time.time()

    return end_time - start_time


def main():

    tests = [(20, 0.1), (20, 0.8), (100, 0.1), (100, 0.8)]

    bfs_results = []
    dfs_results = []
    nx_results = []

    for test in tests:
        size = test[0]
        p = test[1]
        print("Testing with graph size {} and edge probability {}:".format(size, p))

        bfs_results.append(find_with_bfs(size, p))
        print("\tBreadth first search: {0:.6f}s".format(bfs_results[-1]))

        dfs_results.append(find_with_dfs(size, p))
        print("\tDepth first search: {0:.6f}s".format(dfs_results[-1]))

        nx_results.append(find_with_nx(size, p, 'dfs'))
        print("\tNetworkX search: {0:.6f}s".format(nx_results[-1]))


    n = len(tests)

    # convert to milliseconds
    for i in range(n):
        bfs_results[i] *= 1000
        dfs_results[i] *= 1000
        nx_results[i] *= 1000

    def abs_results():
        fig = plt.subplot(211)

        ind = np.arange(n)  # the x locations for the groups
        width = 0.35

        plt.barh(ind, bfs_results, width, color='blue')
        plt.barh(ind + width * 0.5, nx_results, width, color='green')
        plt.barh(ind + width, dfs_results, width, color='red')

        fig.set_xlabel('Time (milliseconds)')
        fig.set_yticklabels(tests, fontdict=None, minor=False)
        fig.set_yticks([x for x in range(n)], minor=False)

    def relative_results():
        ax = plt.subplot(212)

        for i in range(n):
            x = min(100/bfs_results[i], 100/nx_results[i], 100/dfs_results[i])
            bfs_results[i] *= x
            dfs_results[i] *= x
            nx_results[i] *= x

        ind = np.arange(n)  # the x locations for the groups
        width = 0.35

        bfs_bar = plt.barh(ind, bfs_results, width, color='blue')
        nx_bar = plt.barh(ind + width * 0.5, nx_results, width, color='green')
        dfs_bar = plt.barh(ind + width, dfs_results, width, color='red')

        ax.set_ylabel('Nodes, Edge probability')
        ax.set_xlabel('Time (percents)')
        ax.set_yticklabels(tests, fontdict=None, minor=False)
        ax.set_yticks([x for x in range(n)], minor=False)

        ax.legend((bfs_bar, dfs_bar, nx_bar), ('BFS', 'DFS', 'NXD'), loc='upper right',
                  bbox_to_anchor=(1.12, 1.29), fancybox=True, shadow=True)

    abs_results()
    relative_results()
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