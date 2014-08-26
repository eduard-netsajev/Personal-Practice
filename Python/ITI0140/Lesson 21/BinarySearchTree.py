__author__ = 'Netšajev'


class BinarySearchTree:

    def __init__(self):
        self.root = None

    def __iter__(self):
        return self.root.__iter__()

    def max_depth(self):
        temp_depth = 0
        for node in self.root.__iter__():
            temp_depth = max(node.get_depth(), temp_depth)
        return temp_depth

    def min_value(self):
        """
        Returns the left-most node, not the
        node with the lowest counter value
        """
        current_node = self.root
        if current_node:
            while current_node.has_left_child():
                current_node = current_node.left_child
            return current_node
        else:
            return None

    def print_tree(self, node, words=None):

        if node is None:
            return

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
                self.update_balance(current_node.left_child)
        elif word > current_node.word:
            if current_node.has_right_child():
                self._put(word, current_node.right_child)
            else:
                current_node.right_child = TreeNode(word, parent=current_node)
                self.update_balance(current_node.right_child)
        else:
            current_node.increase_word_count()

    def update_balance(self, node):

        if node.balance_factor > 1 or node.balance_factor < -1:
            self.rebalance(node)
            return

        if node.parent is not None:
            if node.is_left_child():
                    node.parent.balance_factor += 1
            elif node.is_right_child():
                    node.parent.balance_factor -= 1
            if node.parent.balance_factor != 0:
                    self.update_balance(node.parent)

    def rotate_left(self, old_root):
        new_root = old_root.right_child
        old_root.right_child = new_root.left_child
        if new_root.left_child is not None:
            new_root.left_child.parent = old_root
        new_root.parent = old_root.parent
        if old_root.is_root():
            self.root = new_root
        else:
            if old_root.is_left_child():
                    old_root.parent.left_child = new_root
            else:
                old_root.parent.right_child = new_root
        new_root.left_child = old_root
        old_root.parent = new_root
        old_root.balance_factor = old_root.balance_factor + 1 - min(new_root.balance_factor, 0)
        new_root.balance_factor = new_root.balance_factor + 1 + max(old_root.balance_factor, 0)

    def rotate_right(self, old_root):
        new_root = old_root.left_child
        old_root.left_child = new_root.right_child
        if new_root.right_child is not None:
            new_root.right_child.parent = old_root
        new_root.parent = old_root.parent
        if old_root.is_root():
            self.root = new_root
        else:
            if old_root.is_right_child():
                    old_root.parent.right_child = new_root
            else:
                old_root.parent.left_child = new_root
        new_root.right_child = old_root
        old_root.parent = new_root
        old_root.balance_factor = old_root.balance_factor - 1 - max(new_root.balance_factor, 0)
        new_root.balance_factor = new_root.balance_factor - 1 + min(old_root.balance_factor, 0)

    def rebalance(self, node):
        if node.balance_factor < 0:
            if node.right_child.balance_factor > 0:
                self.rotate_right(node.right_child)
                self.rotate_left(node)
            else:
                self.rotate_left(node)
        elif node.balance_factor > 0:
            if node.left_child.balance_factor < 0:
                self.rotate_left(node.left_child)
                self.rotate_right(node)
            else:
                self.rotate_right(node)

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
        self.balance_factor = 0

        if self.has_left_child():
            self.left_child.parent = self
        if self.has_right_child():
            self.right_child.parent = self

    def __iter__(self):
        if self:
            if self.has_left_child():
                for elem in self.left_child:
                    yield elem
            yield self
            if self.has_right_child():
                for elem in self.right_child:
                    yield elem

    def get_depth(self):
        if self.is_leaf():
            depth = 1
            current_node = self
            while current_node.has_parent():
                depth += 1
                current_node = current_node.parent
            return depth
        return 0

    def get_count(self):
        return self.count

    def has_parent(self):
        return self.parent

    def has_left_child(self):
        return self.left_child

    def has_right_child(self):
        return self.right_child

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
        if len(result) > 0:
            yield result


def main():
    filename = "Carcosa.txt"

    words_tree = BinarySearchTree()

    my_file = open(filename)
    for word in process_file(my_file):
        words_tree.put(word)
    words = [(node.word, node.count) for node in words_tree.__iter__()]

    print(words)
    print(words_tree.min_value())
    print(words_tree.root)
    print(words_tree.search('liberty'))
    words_tree.print_tree(words_tree.search('above'))
    print("\nMaximum depth: {}".format(words_tree.max_depth()))

if __name__ == "__main__":
    main()