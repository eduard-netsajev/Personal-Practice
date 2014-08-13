__author__ = 'Netšajev'

from nose.tools import *
from ex49 import lexicon


def test_directions():
    assert_equal(lexicon.scan("north"), [('direction', 'north')])
    test = lexicon.scan("north east south")
    assert_equal(test, [('direction', 'north'),
                        ('direction', 'east'),
                        ('direction', 'south')])


def test_verbs():
    assert_equal(lexicon.scan("go"), [('verb', 'go')])
    test = lexicon.scan("go kill eat")
    assert_equal(test, [('verb', 'go'),
                        ('verb', 'kill'),
                        ('verb', 'eat')])


def test_stops():
    assert_equal(lexicon.scan("the"), [('stop', 'the')])
    test = lexicon.scan("the in of")
    assert_equal(test, [('stop', 'the'),
                        ('stop', 'in'),
                        ('stop', 'of')])


def test_nouns():
    assert_equal(lexicon.scan("bear"), [('noun', 'bear')])
    test = lexicon.scan("bear princess")
    assert_equal(test, [('noun', 'bear'),
                        ('noun', 'princess')])


def test_numbers():
    assert_equal(lexicon.scan("1234"), [('number', 1234)])
    test = lexicon.scan("3 91234")
    assert_equal(test, [('number', 3),
                        ('number', 91234)])


def test_errors():
    assert_equal(lexicon.scan("ASDFADFASDF"), [('error', 'asdfadfasdf')])
    test = lexicon.scan("bear IAS princess")
    assert_equal(test, [('noun', 'bear'),
                        ('error', 'ias'),
                        ('noun', 'princess')])


def test_all():
    test = lexicon.scan("ATTACK! the Fucking beaR at north in 3..2..1...")
    assert_equal(test, [('verb', 'attack'),
        ('stop', 'the'),
        ('error', 'fucking'),
        ('noun', 'bear'),
        ('stop', 'at'),
        ('direction', 'north'),
        ('stop', 'in'),
        ('number', 321)])


def test_book():
    test = lexicon.scan("go north")
    assert_equal(test, [('verb', 'go'), ('direction', 'north')])
    test = lexicon.scan("kill the princess")
    assert_equal(test, [('verb', 'kill'), ('stop', 'the'), ('noun', 'princess')])
    test = lexicon.scan("eat the bear")
    assert_equal(test, [('verb', 'eat'), ('stop', 'the'), ('noun', 'bear')])
    test = lexicon.scan("open the door and smack the bear in the nose")
    assert_equal(test, [('verb', 'open'), ('stop', 'the'), ('noun', 'door'), ('error', 'and'),
    ('error', 'smack'), ('stop', 'the'), ('noun', 'bear'), ('stop', 'in'),
    ('stop', 'the'), ('error', 'nose')])