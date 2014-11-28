__author__ = 'Netšajev'

import networkx as nx
import random


def main():

    tests = [(20, 0.2), (20, 0.4), (100, 0.2), (100, 0.4),
             (200, 0.2), (200, 0.4), (1000, 0.2), (1000, 0.4)]

    bfsd_results = []
    bfsn_results = []
    bfsg_results = []

    for test in tests:
        size = test[0]
        p = test[1]

        G = nx.fast_gnp_random_graph(size, p, '2_131654')

        ok = False
        start = -1
        end = -1
        while not ok:
            start = random.randrange(0, size)
            end = random.randrange(0, size)
            if start != end and nx.has_path(G, start, end):
                ok = True

        print("Testing with graph size {} and edge probability {}:".format(size, p))

        bfsd_results.append(bfs_dumb(G, start, end))
        print("\tBFS no-memory: {}".format(bfsd_results[-1]))

        bfsn_results.append(bfs_visited(G, start, end))
        print("\tBFS visited-memory: {}".format(bfsn_results[-1]))

        bfsg_results.append(bfs_seen(G, start, end))
        print("\tBFS seen-memory: {}".format(bfsg_results[-1]))


def bfs_visited(graph, start, end):
    queued = [start]
    visited = []
    while len(queued) > 0:
        t = queued.pop()
        visited.append(t)
        for node in graph[t]:
            if node == end:
                return len(visited)

            if node not in visited:
                queued.insert(0, node)


def bfs_seen(graph, start, end):
    queued = [start]
    seen = [start]
    visited = []
    while len(queued) > 0:
        t = queued.pop()
        visited.append(t)
        for node in graph[t]:
            if node == end:
                return len(visited)
            if node not in seen:
                seen.append(node)
                queued.insert(0, node)


def bfs_dumb(graph, start, end):
    queued = [start]
    visited = []
    while len(queued) > 0:
        t = queued.pop()
        visited.append(t)
        for node in graph[t]:
            if node == end:
                return len(visited)
            queued.insert(0, node)


if __name__ == "__main__":
    main()