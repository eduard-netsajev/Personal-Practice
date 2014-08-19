__author__ = 'Netšajev'

import re

#https://cs.uwaterloo.ca/~dtompkin/teaching/08a/lab7/

failed = 0
succeeded = 0


def check_pattern(regex, expected_result, test_id, string):
    """
    Function for checking regex pattern

    :param regex: regex pattern as string input
    :param string: string for the test
    :param test_id: test id for the error message
    :param expected_result: 1 or 0, True or False, should pass or fail
    :return: print of fail (in case of failure) and increment of global (int) fails
    """
    global failed
    global succeeded
    result = len(re.findall(regex, string))

    """
    exp     res     FAIL?
    --------------------
    0   |   0   |   0
    0   |   1   |   1
    1   |   0   |   1
    1   |   1   |   0
    """
    if expected_result ^ result:
        failed += 1
        print("Test", test_id, "failed")
    else:
        succeeded += 1


def main():

    pattern = '^[01]*$'

    check_pattern(pattern, 1, '1_01', '0')
    check_pattern(pattern, 1, '1_02', '1')
    check_pattern(pattern, 1, '1_03', '00')
    check_pattern(pattern, 1, '1_04', '0')
    check_pattern(pattern, 1, '1_05', '111010101010111')
    check_pattern(pattern, 0, '1_06', 'cat')
    check_pattern(pattern, 0, '1_07', '123')
    check_pattern(pattern, 0, '1_08', 'he110, w0r1d')

    pattern = '^[01]*0$'

    check_pattern(pattern, 1, '2_01', '0')
    check_pattern(pattern, 1, '2_02', '10')
    check_pattern(pattern, 1, '2_03', '1110')
    check_pattern(pattern, 1, '2_04', '010')
    check_pattern(pattern, 1, '2_05', '00011000110110')
    check_pattern(pattern, 0, '2_06', '1')
    check_pattern(pattern, 0, '2_07', '011')
    check_pattern(pattern, 0, '2_08', '0101')
    check_pattern(pattern, 0, '2_09', '0001101')

    pattern = '^([01]{2})*$'

    check_pattern(pattern, 1, '3_01', '00')
    check_pattern(pattern, 1, '3_02', '01')
    check_pattern(pattern, 1, '3_03', '1010')
    check_pattern(pattern, 1, '3_04', '0001')
    check_pattern(pattern, 1, '3_05', '0001101100011011')
    check_pattern(pattern, 0, '3_06', '0')
    check_pattern(pattern, 0, '3_07', '1')
    check_pattern(pattern, 0, '3_08', '111')
    check_pattern(pattern, 0, '3_09', '010')
    check_pattern(pattern, 0, '3_10', '0001101')

    pattern = '^[01]*(0110|1001)[01]*$'

    check_pattern(pattern, 1, '4_01', '0110')
    check_pattern(pattern, 1, '4_02', '1001')
    check_pattern(pattern, 1, '4_03', '01101001')
    check_pattern(pattern, 1, '4_04', '011001')
    check_pattern(pattern, 1, '4_05', '111101101111')
    check_pattern(pattern, 1, '4_06', '0000010010000')
    check_pattern(pattern, 0, '4_07', '0')
    check_pattern(pattern, 0, '4_08', '1')
    check_pattern(pattern, 0, '4_09', '0101')
    check_pattern(pattern, 0, '4_10', '1010')
    check_pattern(pattern, 0, '4_11', '1100010001110')

    pattern = '^[01]*((0110[01]*1001)|(1001[01]*0110)|011001|100110)[01]*$'

    check_pattern(pattern, 1, '5_01', '01101001')
    check_pattern(pattern, 1, '5_02', '000110001001000')
    check_pattern(pattern, 1, '5_03', '011001')
    check_pattern(pattern, 1, '5_04', '100110')
    check_pattern(pattern, 1, '5_05', '000011001000')
    check_pattern(pattern, 0, '5_06', '0')
    check_pattern(pattern, 0, '5_07', '1')
    check_pattern(pattern, 0, '5_08', '0110')
    check_pattern(pattern, 0, '5_09', '1001')
    check_pattern(pattern, 0, '5_10', '01101')
    check_pattern(pattern, 0, '5_11', '10010')
    check_pattern(pattern, 0, '5_12', '1100010001110')

    pattern = '^[a-z]*(\s|-)?[a-z]*\s[a-z]*$'

    check_pattern(pattern, 1, '9_01', 'pickup truck')
    check_pattern(pattern, 1, '9_02', 'pick up truck')
    check_pattern(pattern, 1, '9_03', 'pick-up truck')
    check_pattern(pattern, 0, '9_04', 'pickuptruck')
    check_pattern(pattern, 0, '9_05', 'pick.up.truck')
    check_pattern(pattern, 0, '9_06', 'pick- up truck')
    check_pattern(pattern, 0, '9_07', 'pick -up truck')
    check_pattern(pattern, 0, '9_08', 'volkswagon')


    pattern = '^\S*\s\S*\s\S*(\s\S*)?$'

    check_pattern(pattern, 1, '10_01', 'one two three')
    check_pattern(pattern, 1, '10_02', 'one two three four')
    check_pattern(pattern, 1, '10_03', '1 2 3 4')
    check_pattern(pattern, 1, '10_04', 'how many words?')
    check_pattern(pattern, 0, '10_05', 'word')
    check_pattern(pattern, 0, '10_06', 'word word')
    check_pattern(pattern, 0, '10_07', 'word word word word word')

    pattern = '^cat\s(\w*\s)?(\w*\s)?hat$'

    check_pattern(pattern, 1, '11_01', 'cat hat')
    check_pattern(pattern, 1, '11_02', 'cat in hat')
    check_pattern(pattern, 1, '11_03', 'cat in the hat')
    check_pattern(pattern, 0, '11_04', 'hat cat')
    check_pattern(pattern, 0, '11_05', 'cathat')
    check_pattern(pattern, 0, '11_06', 'catinthehat')
    check_pattern(pattern, 0, '11_07', 'cat in the phat hat')
    check_pattern(pattern, 0, '11_08', 'cat in the super phat hat')

    pattern = '^(\d|1\d|2[0-3]):[0-5]\d$'

    check_pattern(pattern, 1, '12_01', '12:34')
    check_pattern(pattern, 1, '12_02', '0:00')
    check_pattern(pattern, 1, '12_03', '23:59')
    check_pattern(pattern, 1, '12_04', '9:00')
    check_pattern(pattern, 1, '12_05', '10:00')
    check_pattern(pattern, 1, '12_06', '11:11')
    check_pattern(pattern, 1, '12_07', '15:15')
    check_pattern(pattern, 0, '12_08', '24:00')
    check_pattern(pattern, 0, '12_09', '12:32 pm')
    check_pattern(pattern, 0, '12_10', '0:60')
    check_pattern(pattern, 0, '12_11', '9:99')
    check_pattern(pattern, 0, '12_12', '04:00')
    check_pattern(pattern, 0, '12_13', '4')
    check_pattern(pattern, 0, '12_14', '-4:00')

    pattern = '^[ACGT]*ATG([ACGT]{3})+(TAA|TAG|TGA)[ACGT]*$'

    check_pattern(pattern, 1, '13_01', 'ATGCCCTAA')
    check_pattern(pattern, 1, '13_02', 'ATGCCCTAG')
    check_pattern(pattern, 1, '13_03', 'ATGCCCTGA')
    check_pattern(pattern, 1, '13_04', 'CATGCCCTAA')
    check_pattern(pattern, 1, '13_05', 'CATGCCCTAG')
    check_pattern(pattern, 1, '13_06', 'CATGCCCTGA')
    check_pattern(pattern, 1, '13_07', 'CATGCCCTAAC')
    check_pattern(pattern, 1, '13_08', 'CATGCCCTAGC')
    check_pattern(pattern, 1, '13_09', 'CATGCCCTGAT')
    check_pattern(pattern, 1, '13_10', 'TCATGCCCTGACC')
    check_pattern(pattern, 1, '13_11', 'TTATGCCCGGGTGACC')
    check_pattern(pattern, 1, '13_12', 'AAACTCATGCCCGGGCCCTGACCTTAA')
    check_pattern(pattern, 1, '13_13', 'ATGATGATGTAA')
    check_pattern(pattern, 1, '13_14', 'ATGAAAAACAAGAATTAA')
    check_pattern(pattern, 1, '13_15', 'ATGACAACCACGACTTAA')
    check_pattern(pattern, 1, '13_16', 'ATGAGAAGCAGGAGTTAA')
    check_pattern(pattern, 1, '13_17', 'ATGATAATCATGATTTAA')
    check_pattern(pattern, 1, '13_18', 'ATGCAACACCAGCATTAA')
    check_pattern(pattern, 1, '13_19', 'ATGCCACCCCCGCCTTAA')
    check_pattern(pattern, 1, '13_20', 'ATGCGACGCCGGCGTTAA')
    check_pattern(pattern, 1, '13_21', 'ATGCTACTCCTGCTTTAA')
    check_pattern(pattern, 1, '13_22', 'ATGGAAGACGAGGATTAA')
    check_pattern(pattern, 1, '13_23', 'ATGGCAGCCGCGGCTTAA')
    check_pattern(pattern, 1, '13_24', 'ATGGGAGGCGGGGGTTAA')
    check_pattern(pattern, 1, '13_25', 'ATGGTAGTCGTGGTTTAA')
    check_pattern(pattern, 1, '13_26', 'ATGTACTATTCATCCTCGTCTTGCTGGTGTTTATTCTTGTTTTAA')
    check_pattern(pattern, 0, '13_27', 'GATTACA')
    check_pattern(pattern, 0, '13_28', 'ATGTAA')
    check_pattern(pattern, 0, '13_29', 'ATGTAG')
    check_pattern(pattern, 0, '13_30', 'ATGTGA')
    check_pattern(pattern, 0, '13_31', 'ATGCCCCTAG')
    check_pattern(pattern, 0, '13_32', 'ATGCCCCCTAG')
    check_pattern(pattern, 0, '13_33', 'CCCATGCCCCTAGCCC')
    check_pattern(pattern, 0, '13_34', 'CCCATGCCCCCTAGCCC')

    pattern = '^\$((\d{1,3}(,\d{3})*)|\d+)(\.\d\d)?$'

    check_pattern(pattern, 1, '14_01', '$0')
    check_pattern(pattern, 1, '14_02', '$0.00')
    check_pattern(pattern, 1, '14_03', '$0.99')
    check_pattern(pattern, 1, '14_04', '$4')
    check_pattern(pattern, 1, '14_05', '$4.00')
    check_pattern(pattern, 1, '14_06', '$10')
    check_pattern(pattern, 1, '14_07', '$10.00')
    check_pattern(pattern, 1, '14_08', '$1000')
    check_pattern(pattern, 1, '14_09', '$1000.00')
    check_pattern(pattern, 1, '14_10', '$1,000')
    check_pattern(pattern, 1, '14_11', '$1,000.00')
    check_pattern(pattern, 1, '14_12', '$8,888,888,888,888.88')
    check_pattern(pattern, 1, '14_13', '$88,888,888,888,888.88')
    check_pattern(pattern, 1, '14_14', '$888,888,888,888,888.88')
    check_pattern(pattern, 1, '14_15', '$1234567.89')
    check_pattern(pattern, 0, '14_16', '$-0')
    check_pattern(pattern, 0, '14_17', '$ 0')
    check_pattern(pattern, 0, '14_18', '$1.9')
    check_pattern(pattern, 0, '14_19', '$.99')
    check_pattern(pattern, 0, '14_20', 'bannana')
    check_pattern(pattern, 0, '14_21', '$,333.33')
    check_pattern(pattern, 0, '14_22', '$12,34')
    check_pattern(pattern, 0, '14_23', '$22,333,22,333.22')
    check_pattern(pattern, 0, '14_24', '$$$')
    check_pattern(pattern, 0, '14_25', '$$$0')
    check_pattern(pattern, 0, '14_26', '3$')

    pattern = '^([1]*(0[1]*0)*[1]*)*$'

    check_pattern(pattern, 1, '16_01', '1')
    check_pattern(pattern, 1, '16_02', '11')
    check_pattern(pattern, 1, '16_03', '111')
    check_pattern(pattern, 1, '16_04', '1111')
    check_pattern(pattern, 1, '16_05', '100')
    check_pattern(pattern, 1, '16_06', '001')
    check_pattern(pattern, 1, '16_07', '010')
    check_pattern(pattern, 1, '16_08', '1001')
    check_pattern(pattern, 1, '16_09', '100001')
    check_pattern(pattern, 1, '16_10', '1001001')
    check_pattern(pattern, 1, '16_11', '1010')
    check_pattern(pattern, 1, '16_12', '101101')
    check_pattern(pattern, 1, '16_13', '111101110111')
    check_pattern(pattern, 1, '16_14', '00')
    check_pattern(pattern, 1, '16_15', '0000')
    check_pattern(pattern, 1, '16_16', '000000')
    check_pattern(pattern, 1, '16_17', '1111011111110111110111110111')
    check_pattern(pattern, 1, '16_18', '1101110111100111101110111111')
    check_pattern(pattern, 0, '16_19', '0')
    check_pattern(pattern, 0, '16_20', '000')
    check_pattern(pattern, 0, '16_21', '00000')
    check_pattern(pattern, 0, '16_22', '10')
    check_pattern(pattern, 0, '16_23', '01')
    check_pattern(pattern, 0, '16_24', '110')
    check_pattern(pattern, 0, '16_25', '011')
    check_pattern(pattern, 0, '16_26', '0001')
    check_pattern(pattern, 0, '16_27', '1000')
    check_pattern(pattern, 0, '16_28', '10001')
    check_pattern(pattern, 0, '16_29', '101001')
    check_pattern(pattern, 0, '16_30', '1010101')
    check_pattern(pattern, 0, '16_31', '11111111110')
    check_pattern(pattern, 0, '16_32', '01111111111')
    check_pattern(pattern, 0, '16_33', '11110111111')

if __name__ == '__main__':
    main()
    print("Succeeded", succeeded, "tests.")
    print("Failed", failed, "tests.")