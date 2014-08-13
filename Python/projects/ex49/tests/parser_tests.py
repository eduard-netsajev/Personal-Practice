__author__ = 'Netšajev'

from nose.tools import *
from ex49 import parser


def test_peek():
    assert_equal(parser.peek([('verb', 'attack')]), 'verb')
    assert_equal(parser.peek([('direction', 'south')]), 'direction')
    assert_equal(parser.peek([('noun', 'bear')]), 'noun')
    assert_equal(parser.peek([('error', 'Eduard')]), 'error')
    assert_equal(parser.peek([('noun', 'door'), ('direction', 'west')]), 'noun')
    assert_equal(parser.peek([('direction', 'west'), ('noun', 'door')]), 'direction')
    assert_equal(parser.peek([('verb', 'go'), ('direction', 'north')]), 'verb')


def test_match():
    words_list = [('verb', 'go')]
    assert_equal(parser.match(words_list, 'verb'), ('verb', 'go'))
    assert_equal(words_list, [])

    words_list = [('direction', 'north')]
    assert_equal(parser.match(words_list, 'direction'), ('direction', 'north'))
    assert_equal(words_list, [])

    words_list = [('noun', 'princess')]
    assert_equal(parser.match(words_list, 'noun'), ('noun', 'princess'))
    assert_equal(words_list, [])

    words_list = [('number', 8)]
    assert_equal(parser.match(words_list, 'number'), ('number', 8))
    assert_equal(words_list, [])

    words_list = [('noun', 'door'), ('direction', 'west')]
    assert_equal(parser.match(words_list, 'noun'), ('noun', 'door'))
    assert_equal(words_list, [('direction', 'west')])

    words_list = [('noun', 'door'), ('direction', 'west')]
    assert_equal(parser.match(words_list, 'verb'), None)
    assert_equal(words_list, [('direction', 'west')])

    words_list = []
    assert_equal(parser.match(words_list, 'error'), None)


def test_skip():

    words_list = [('verb', 'go'), ('verb', 'attack')]
    parser.skip(words_list, 'verb')
    assert_equal(words_list, [])

    words_list = [('verb', 'go'), ('direction', 'north'), ('verb', 'attack')]
    parser.skip(words_list, 'verb')
    assert_equal(words_list, [('direction', 'north'), ('verb', 'attack')])

    words_list = [('verb', 'go'), ('direction', 'north'), ('verb', 'attack')]
    parser.skip(words_list, 'direction')
    assert_equal(words_list, [('verb', 'go'), ('direction', 'north'), ('verb', 'attack')])

    words_list = [('noun', 'door'), ('direction', 'west')]
    parser.skip(words_list, 'noun')
    assert_equal(words_list, [('direction', 'west')])

    words_list = [('noun', 'door'), ('direction', 'west')]
    parser.skip(words_list, 'verb')
    assert_equal(words_list, [('noun', 'door'), ('direction', 'west')])

    words_list = []
    parser.skip(words_list, 'error')
    assert_equal(words_list, [])


def test_parse_verb():

    word_list = [('verb', 'go'), ('direction', 'north')]
    assert_equal(parser.parse_verb(word_list), ('verb', 'go'))
    assert_equal(word_list, [('direction', 'north')])

    word_list = [('verb', 'kill'), ('stop', 'the'), ('noun', 'princess')]
    assert_equal(parser.parse_verb(word_list), ('verb', 'kill'))
    assert_equal(word_list, [('stop', 'the'), ('noun', 'princess')])

    word_list = [('verb', 'eat'), ('stop', 'the'), ('noun', 'bear')]
    assert_equal(parser.parse_verb(word_list), ('verb', 'eat'))
    assert_equal(word_list, [('stop', 'the'), ('noun', 'bear')])

    word_lst = [('error', 'open'), ('stop', 'the'), ('noun', 'door'), ('error', 'and'),
                ('error', 'smack'), ('stop', 'the'), ('noun', 'bear'), ('stop', 'in'),
                ('stop', 'the'), ('error', 'nose')]
    old_word_list = word_lst
    assert_raises(parser.ParserError, parser.parse_verb, word_lst)
    assert_equal(word_lst, old_word_list)

    word_list = [('stop', 'from'), ('stop', 'to'), ('verb', 'attack'), ('stop', 'the'), ('noun', 'bear')]
    assert_equal(parser.parse_verb(word_list), ('verb', 'attack'))
    assert_equal(word_list, [('stop', 'the'), ('noun', 'bear')])


def test_parse_object():
    word_list = [('noun', 'player'), ('stop', 'to'), ('verb', 'attack'), ('stop', 'the'), ('noun', 'bear')]
    assert_equal(parser.parse_object(word_list), ('noun', 'player'))
    assert_equal(word_list, [('stop', 'to'), ('verb', 'attack'), ('stop', 'the'), ('noun', 'bear')])

    word_list = [('noun', 'door'), ('direction', 'west')]
    assert_equal(parser.parse_object(word_list), ('noun', 'door'))
    assert_equal(parser.parse_object(word_list), ('direction', 'west'))
    assert_equal(word_list, [])

    word_list = [('verb', 'go'), ('direction', 'north')]
    assert_raises(parser.ParserError, parser.parse_object, word_list)


def test_parse_subject():

    word_list = [('stop', 'the'), ('stop', 'to'), ('verb', 'attack'), ('stop', 'the'), ('noun', 'bear')]
    assert_equal(parser.parse_subject(word_list, ('noun', 'player')).phrase,
                 parser.Sentence(('noun', 'player'), ('verb', 'attack'), ('noun', 'bear')).phrase)

    word_list = [('stop', 'to'), ('verb', 'go'),
                 ('stop', 'to'), ('stop', 'the'), ('direction', 'east')]
    assert_equal(parser.parse_subject(word_list, ('noun', 'princess')).phrase,
                 parser.Sentence(('noun', 'princess'), ('verb', 'go'), ('direction', 'east')).phrase)

    word_list = [('stop', 'to'), ('stop', 'the'), ('direction', 'east')]
    assert_raises(parser.ParserError, parser.parse_subject, word_list, ('noun', 'princess'))


def test_parse_sentence():

    word_list = [('stop', 'to'), ('verb', 'attack'), ('stop', 'the'), ('noun', 'bear')]
    assert_equal(parser.parse_sentence(word_list).phrase,
                 parser.Sentence(('noun', 'player'), ('verb', 'attack'), ('noun', 'bear')).phrase)

    word_list = [('noun', 'princess'), ('stop', 'to'), ('verb', 'go'),
                 ('stop', 'to'), ('stop', 'the'), ('direction', 'east')]
    assert_equal(parser.parse_sentence(word_list).phrase,
                 parser.Sentence(('noun', 'princess'), ('verb', 'go'), ('direction', 'east')).phrase)

    word_list = [('stop', 'to'), ('stop', 'the'), ('direction', 'east')]
    assert_raises(parser.ParserError, parser.parse_sentence, word_list)