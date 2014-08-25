__author__ = 'Netšajev'

import Dijkstra as deik
import networkx as nx
import unittest
import random


class TestDijkstra(unittest.TestCase):

    def test_1(self):
        x = deik.generate_graph(21, 131654)
        s, t = 8, 14
        self.assertEqual(deik.dijkstra(x, s, t), nx.dijkstra_path(x, s, t))

    def test_2(self):
        size = random.randrange(1, 131)
        x = deik.generate_graph(size, 131654)
        s = random.randrange(size)
        t = random.randrange(size)
        self.assertEqual(deik.dijkstra(x, s, t), nx.dijkstra_path(x, s, t))

    def test_3(self):
        x = deik.generate_graph(654, 131654)
        s, t = 131, 654
        self.assertRaises(ValueError, deik.dijkstra, x, s, t)
        self.assertRaises(nx.NetworkXNoPath, nx.dijkstra_path, x, s, t)