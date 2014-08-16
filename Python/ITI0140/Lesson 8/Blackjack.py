__author__ = 'Netšajev'


suits = ('S', 'C', 'H', 'D')
vals = ('2', '3', '4', '5',
        '6', '7', '8', '9', 'T')
courts = ('J', 'Q', 'K', 'A')


def control_input(card_list):
    """
    Function to validate the list of cards

    :param card_list: List of cards
    :return: are all items in the list valid cards
    """
    try:
        for x in card_list:
            if len(x) == 2:
                if x[0] in suits and (x[1] in vals or x[1] in courts):
                    pass
                else:
                    return False
            else:
                return False
        return True
    except TypeError:
        return False


def card_value(card):
    """
    Function for finding a value of given card

    :param card: 2-char string
    :return: int value
    """
    value = card[1]
    if value in vals:
        return vals.index(value) + 2
    elif value == 'A':
        return 11
    else:
        return 10


def cards_sum(card_list):
    """
    Function for calculating the sum of given cards

    :param card_list: list of valid cards
    :return: card list sum
    """
    aces = 0
    result = 0
    for card in card_list:
        value = card_value(card)
        result += value
        if value == 11:
            aces += 1

    while result > 21 and aces > 0:
        result -= 10
        aces -= 1

    return result


def decide_action(points):
    """
    Decides what should dealer do depending
    on his cards. Passes starting from 17 points

    :param points: sum of card values
    :return: string of decided action
    """
    if points > 21:
        return 'Busted!'
    elif points == 21:
        return 'Blackjack!'
    elif points >= 17:
        return 'Pass!'
    else:
        return 'Hit me!'


def main():
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

    lists = [list0, list1, list2, list3, list4,
             list5, list6, list7, list8, list9]
    for some_list in lists:
        if control_input(some_list):
            print(some_list, cards_sum(some_list))
            print(decide_action(cards_sum(some_list)))


if __name__ == '__main__':
    main()