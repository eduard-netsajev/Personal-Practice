__author__ = 'Netšajev'


class BinarySearchTree:

    def __init__(self):
        self.root = None

    def __iter__(self):
        return self.root.__iter__()

    def max_depth(self):
        temp_depth = 0
        for node in self.root.__iter__():
            temp_depth = max(node.depth, temp_depth)
        return temp_depth

    def min_value(self):
        current_node = self.root
        if current_node:
            while current_node.has_left_child():
                current_node = current_node.left_child
            return current_node
        else:
            return None

    def print_tree(self, node, words=None):
        # Short way with Node __iter__: print(list(node.__iter__()))
        flag_start = False
        if words is None:
            flag_start = True
            words = []

        if node.has_left_child():
            self.print_tree(node.left_child, words)

        words.append((node.word, node.count))

        if node.has_right_child():
            self.print_tree(node.right_child, words)

        if flag_start:
            print(words)
            return words

    def search(self, word):
        current_node = self.root
        while current_node:
            if word == current_node.word:
                return current_node
            elif word > current_node.word:
                current_node = current_node.right_child
            elif word < current_node.word:
                current_node = current_node.left_child
            else:
                break

        print("Word {} was not found in the tree".format(word))
        return None

    def put(self, word):
        if self.root:
            self._put(word, self.root)
        else:
            self.root = TreeNode(word)

    def _put(self, word, current_node):

        if word < current_node.word:
            if current_node.has_left_child():
                self._put(word, current_node.left_child)
            else:
                current_node.left_child = TreeNode(word, parent=current_node)
        elif word > current_node.word:
            if current_node.has_right_child():
                self._put(word, current_node.right_child)
            else:
                current_node.right_child = TreeNode(word, parent=current_node)
        else:
            current_node.increase_word_count()

    def get(self, word):
        if self.root:
            res = self._get(word, self.root)
            if res:
                return res.count
            else:
                return None
        else:
            return None

    def _get(self, word, current_node):
        if not current_node:
            return None
        elif current_node.word == word:
            return current_node
        elif word < current_node.word:
            return self._get(word, current_node.left_child)
        else:
            return self._get(word, current_node.right_child)

    def __getitem__(self, word):
        return self.get(word)

    def __contains__(self, word):
        if self._get(word, self.root):
            return True
        else:
            return False


class TreeNode:
    def __init__(self, word, left=None, right=None, parent=None):
        self.word = word
        self.count = 1
        self.left_child = left
        self.right_child = right
        self.parent = parent

        if self.has_left_child():
            self.left_child.parent = self
        if self.has_right_child():
            self.right_child.parent = self
        if self.parent:
            self.depth = self.parent.depth + 1
        else:
            self.depth = 1

    def __iter__(self):
        if self:
            if self.has_left_child():
                for elem in self.left_child:
                    yield elem
            yield self
            if self.has_right_child():
                for elem in self.right_child:
                    yield elem

    def get_count(self):
        return self.count

    def has_left_child(self):
        return self.left_child

    def has_right_child(self):
        return self.right_child

    """
    def is_left_child(self):
        return self.parent and self.parent.left_child == self

    def is_right_child(self):
        return self.parent and self.parent.right_child == self

    def is_root(self):
        return not self.parent

    def is_leaf(self):
        return not (self.right_child or self.left_child)

    def has_any_children(self):
        return self.right_child or self.left_child

    def has_both_children(self):
        return self.right_child and self.left_child
    """

    def increase_word_count(self):
        self.count += 1

    def __str__(self):
        return str((self.word, self.count))


def process_file(opened_file):
    """Remove non-alphanumeric & set lowercase"""
    result = ""
    for line in opened_file:
        line = line.lower()
        for letter in line:
            if letter.isalpha():
                result += letter
            else:
                if len(result) > 0:
                    yield result
                    result = ""


def main():

    filename = "Carcosa.txt"

    words_tree = BinarySearchTree()

    my_file = open(filename)
    for word in process_file(my_file):
        words_tree.put(word)

    words = [(node.word, node.count) for node in words_tree.__iter__()]
    print(words)

    print(words_tree.min_value())

    print(words_tree.search('liberty'))

    words_tree.print_tree(words_tree.search('advanced'))

    print(words_tree.max_depth())

if __name__ == "__main__":
    main()