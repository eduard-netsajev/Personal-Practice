__author__ = 'Netšajev'

import roman_numerals as rn
import unittest

roman1 = "xxi"  # 21 OK
roman2 = "CCXXXIV"  # 234 OK
roman3 = "xii"  # 12 OK
roman4 = "CXXIII"  # 123 OK
roman5 = "I"  # 1 OK
roman6 = "-XII"  # 21 OK
roman7 = "fdsgf"  # NOT OK
roman8 = "IIX"  # NOT OK
roman9 = "IIIIV"  # NOT OK
roman10 = "DXCV"  # 595 OK
roman11 = "MCDL"  # 1450 OK
roman12 = "XCV"  # 95 OK
roman13 = "CXXXV"  # 135 OK
roman14 = "CXILV"  # 165 OK
roman15 = "CXCV"  # 195 OK
roman16 = ""  # 0 OK
roman17 = "XCIX"  # 99 OK
roman18 = {"date": 'X'}
roman19 = ['V', 'I']
roman20 = {'V', 'I'}
roman21 = 21

# You would not put more than one smaller number in front of a larger number to subtract.
# Program as now doesn't allow even one smaller number
# ex. XCIX although in docs "That means that 99 is XCIX"


class TestConvert(unittest.TestCase):
    def test_1(self):
        self.assertEqual(rn.convert(roman1), 21)

    def test_2(self):
        self.assertEqual(rn.convert(roman2), 234)

    def test_3(self):
        self.assertEqual(rn.convert(roman3), 12)

    def test_4(self):
        self.assertEqual(rn.convert(roman4), 123)

    def test_5(self):
        self.assertEqual(rn.convert(roman5), 1)

    def test_6(self):
        self.assertEqual(rn.convert(roman6), -1)

    def test_7(self):
        self.assertEqual(rn.convert(roman7), -1)

    def test_8(self):
        self.assertEqual(rn.convert(roman8), -1)

    def test_9(self):
        self.assertEqual(rn.convert(roman9), -1)

    def test_10(self):
        self.assertEqual(rn.convert(roman10), 595)

    def test_11(self):
        self.assertEqual(rn.convert(roman11), 1450)

    def test_12(self):
        self.assertEqual(rn.convert(roman12), 95)

    def test_13(self):
        self.assertEqual(rn.convert(roman13), 135)

    def test_14(self):
        self.assertEqual(rn.convert(roman14), -1)

    def test_15(self):
        self.assertEqual(rn.convert(roman15), 195)

    def test_16(self):
        self.assertEqual(rn.convert(roman16), 0)

    def test_17(self):
        self.assertEqual(rn.convert(roman17), 99)

    def test_18(self):
        self.assertEqual(rn.convert(roman18), -1)

    def test_19(self):
        self.assertEqual(rn.convert(roman19), -1)

    def test_20(self):
        self.assertEqual(rn.convert(roman20), -1)

    def test_21(self):
        self.assertEqual(rn.convert(roman21), -1)

    def test_22(self):
        self.assertEqual(rn.convert("MMMMDCCCCLXXXXVIIII"), 4999)

    def test_23(self):
        self.assertEqual(rn.convert("MMCMXC"), 2990)
