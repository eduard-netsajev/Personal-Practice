__author__ = 'Netšajev'

import unittest
import SearchAlgorithms as algos
import random
import networkx as nx


class TestDFS(unittest.TestCase):

    def test_1(self):
        x = algos.generate_graph(654, 131654)
        n = 131
        self.assertEqual(algos.dfs_search(x, n), list(nx.dfs_edges(x, n)))

    def test_2(self):
        x = algos.generate_graph(5, 131654)
        n = 1
        self.assertEqual(algos.dfs_search(x, n), [(1, 2), (2, 0), (2, 3), (3, 4)])

    def test_3(self):
        x = algos.generate_graph(13, 131654)
        n = random.randint(0, 10)
        self.assertEqual(algos.dfs_search(x, n), list(nx.dfs_edges(x, n)))

    def test_4(self):
        x = algos.generate_graph(100, 131654)
        n = random.randint(40, 90)
        self.assertEqual(algos.dfs_search(x, n), list(nx.dfs_edges(x, n)))

    def test_5(self):
        x = algos.generate_graph(1000, 131654)
        n = random.randint(400, 900)
        self.assertEqual(algos.dfs_search(x, n), list(nx.dfs_edges(x, n)))


class TestBFS(unittest.TestCase):

    def test_1(self):
        x = algos.generate_graph(654, 131654)
        n = 131
        self.assertEqual(algos.bfs_search(x, n), list(nx.bfs_edges(x, n)))

    def test_2(self):
        x = algos.generate_graph(5, 131654)
        n = 1
        self.assertEqual(algos.bfs_search(x, n), [(1, 2), (1, 4), (2, 0), (2, 3)])

    def test_3(self):
        x = algos.generate_graph(13, 131654)
        n = random.randint(0, 10)
        self.assertEqual(algos.bfs_search(x, n), list(nx.bfs_edges(x, n)))

    def test_4(self):
        x = algos.generate_graph(100, 131654)
        n = random.randint(40, 90)
        self.assertEqual(algos.bfs_search(x, n), list(nx.bfs_edges(x, n)))

    def test_5(self):
        x = algos.generate_graph(1000, 131654)
        n = random.randint(400, 900)
        self.assertEqual(algos.bfs_search(x, n), list(nx.bfs_edges(x, n)))

