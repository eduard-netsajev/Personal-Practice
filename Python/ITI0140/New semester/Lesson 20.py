__author__ = 'Netšajev'

import networkx as nx
import random

stop = False


def main():
    """
    Main function. Comparing DFS with stack and recursive DFS.
    """
    global stop

    tests = [(20, 0.2), (20, 0.4), (100, 0.2), (100, 0.4),
             (200, 0.2), (200, 0.4), (1000, 0.2), (1000, 0.4)]

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

        bfsn_results.append(dfs_stack(G, start, end))
        print("\tDFS stack: {}".format(bfsn_results[-1]))

        visited = []
        stop = False
        bfsg_results.append(dfs_recursion(G, start, end, visited))
        print("\tDFS recursion: {}".format(bfsg_results[-1]))


def dfs_recursion(graph, start, end, visited):
    """
    Recursive DFS search.

    Arguments:
    graph - Graph on which the search is made
    start - Node where we start the search
    end - Node we are looking for
    visited - Shared across all recursive functions list of visited nodes

    Returns:
    result - Number of nodes checked
    """
    global stop
    result = 0

    if start not in visited and not stop:
        result += 1
        visited.append(start)
        for node in graph[start]:
            if node == end:
                stop = True
                return result

            if node not in visited and not stop:
                result += dfs_recursion(graph, node, end, visited)
    return result


def dfs_stack(graph, start, end):
    """
    DFS search with stack.

    Arguments:
    graph - Graph on which the search is made
    start - Node where we start the search
    end - Node we are looking for

    Returns:
    result - Number of nodes checked
    """
    queued = [start]
    seen = [start]
    while len(queued) > 0:
        t = queued.pop()
        for node in graph[t]:
            if node == end:
                result = len(seen)
                return result
            if node not in seen:
                seen.append(node)
                queued.append(node)


if __name__ == "__main__":
    main()