__author__ = 'Netšajev'

import Blackjack as BJ
import unittest


list0 = ['S8', 'CA']
list1 = ['H3', 'S9']
list2 = ['HA', 'CA', 'SQ', 'SK']
list3 = ['H5', 'DA']
list4 = ['H7', 'DK']
list5 = ['SA', 'DT']
list6 = ['KA', 'D1']
list7 = [5, 6, 8]
list8 = 42
list9 = 'string'


class TestControlInput(unittest.TestCase):
    def test_1(self):
        self.assertEqual(BJ.control_input(list0), True)

    def test_2(self):
        self.assertEqual(BJ.control_input(list1), True)

    def test_3(self):
        self.assertEqual(BJ.control_input(list2), True)

    def test_4(self):
        self.assertEqual(BJ.control_input(list6), False)

    def test_5(self):
        self.assertEqual(BJ.control_input(list7), False)

    def test_6(self):
        self.assertEqual(BJ.control_input(list8), False)

    def test_7(self):
        self.assertEqual(BJ.control_input(list9), False)

    def test_8(self):
        self.assertEqual(BJ.control_input(list5), True)


class TestCardValue(unittest.TestCase):
    def test_1(self):
        self.assertEqual(BJ.card_value('S8'), 8)

    def test_2(self):
        self.assertEqual(BJ.card_value('CA'), 11)

    def test_3(self):
        self.assertEqual(BJ.card_value('HQ'), 10)

    def test_4(self):
        self.assertEqual(BJ.card_value('S5'), 5)

    def test_5(self):
        self.assertEqual(BJ.card_value('CT'), 10)

    def test_6(self):
        self.assertEqual(BJ.card_value('DJ'), 10)


class TestCardsSum(unittest.TestCase):
    def test_1(self):
        self.assertEqual(BJ.cards_sum(list0), 19)

    def test_2(self):
        self.assertEqual(BJ.cards_sum(list1), 12)

    def test_3(self):
        self.assertEqual(BJ.cards_sum(list2), 22)

    def test_4(self):
        self.assertEqual(BJ.cards_sum(list3), 16)

    def test_5(self):
        self.assertEqual(BJ.cards_sum(list4), 17)

    def test_6(self):
        self.assertEqual(BJ.cards_sum(list5), 21)


class TestDecideAction(unittest.TestCase):
    def test_1(self):
        self.assertEqual(BJ.decide_action(5), 'Hit me!')

    def test_2(self):
        self.assertEqual(BJ.decide_action(15), 'Hit me!')

    def test_3(self):
        self.assertEqual(BJ.decide_action(16), 'Hit me!')

    def test_4(self):
        self.assertEqual(BJ.decide_action(17), 'Pass!')

    def test_5(self):
        self.assertEqual(BJ.decide_action(18), 'Pass!')

    def test_6(self):
        self.assertEqual(BJ.decide_action(20), 'Pass!')

    def test_7(self):
        self.assertEqual(BJ.decide_action(21), 'Blackjack!')

    def test_8(self):
        self.assertEqual(BJ.decide_action(22), 'Busted!')

    def test_9(self):
        self.assertEqual(BJ.decide_action(23), 'Busted!')


if __name__ == "__main__":
    unittest.main(verbosity=3)