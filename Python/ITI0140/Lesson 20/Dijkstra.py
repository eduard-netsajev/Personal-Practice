__author__ = 'Netšajev'

import networkx as nx
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


def dijkstra(graph, source, target, weight='weight'):
        dist_dict = {source: 0}  # Distance from source to source
        previous = {}
        queue = []

        def min_dist_vertex():
            n = queue[0]
            for v in queue:
                if dist_dict[v] < dist_dict[n]:
                    n = v
            return n

        for vertex in graph:  # Initialization loop
            if vertex != source:
                dist_dict[vertex] = float('Infinity')  # Unknown distance function from source to vertex
                previous[vertex] = None  # Previous node in optimal path from source

            queue.insert(0, vertex)  # All nodes initially in Q (unvisited nodes)

        while len(queue) > 0:  # Main loop
            pass
            u = min_dist_vertex()
            queue.remove(u)
            if u == target:
                seq = []
                u = target
                while u in previous:
                    seq.insert(0, u)
                    u = previous[u]
                seq.insert(0, source)
                return seq

            for neighbor in graph[u]:
                if neighbor not in queue:
                    continue

                alt_dist = dist_dict[u] + graph[u][neighbor][weight]
                if alt_dist < dist_dict[neighbor]:
                    dist_dict[neighbor] = alt_dist
                    previous[neighbor] = u

        raise ValueError(
            "node %s not reachable from %s" % (source, target))


def main():
    G = generate_graph(654, '131654')

    print("NetworkX Dijkstra: ")
    time_1 = time.time()
    print(nx.dijkstra_path(G, 131, 65, weight='weight'))
    time_2 = time.time()
    print("{0:.3f} ms".format((time_2-time_1)*1000))

    print("My Dijkstra: ")
    time_1 = time.time()
    print(dijkstra(G, 131, 65, weight='weight'))
    time_2 = time.time()
    print("{0:.3f} ms".format((time_2-time_1)*1000))


if __name__ == '__main__':
    main()