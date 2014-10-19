import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class AssemblySolution {

    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    static int[] memory;
    static TreeMap<String, Integer> labels;
    static ArrayList<Command> commands;

    static int lastCMPR = -2;

    static int i;

    public static void main(String[] args) throws IOException {

        String memsize = reader.readLine();

        labels = new TreeMap<>();

        commands = new ArrayList<>(100);

        memory = new int[Integer.parseInt(memsize.trim(), 16) + 1];

        String cmd;
        String[] tkns;
        int c = 0;
        int command;
        int arg;
        String[] argus;
        StringTokenizer tokenizer;

        for (cmd = reader.readLine(); cmd != null && cmd.length() > 0; c++) {

            tokenizer = new StringTokenizer(cmd);
            arg = tokenizer.countTokens();

            if (arg == 3) {
                labels.put(tokenizer.nextToken(), c);
            }
            command = command(tokenizer.nextToken());



            tkns = cmd.split(" ");
//            System.out.println(cmd);
            cmd = null;
//            if ( (command = command(tkns[0])) > 0) {
//                arg= 1;
//            } else {
//                labels.put(tkns[0], c);
//                command = command(tkns[1]);
//                arg = 2;
//            }
            argus = tokenizer.nextToken().split(",");

            commands.add(new Command(command, argus));

            cmd = reader.readLine();
        }


        for (i = 0; i < c;) {
            executeCmd(commands.get(i));
        }
    }

    private static void executeCmd(Command cmd) {

        int n1 = 0;
        int t1 = 0;
        int n2 = 0;
        int t2 = 0;

        if(cmd.command < 9) {

            switch (cmd.args[0].charAt(0)) {
                case '#':
                    n1 = Integer.parseInt(cmd.args[0].substring(1), 16);
                    t1 = -1;
                    break;
                case '(':
                    n1 = memory[Integer.parseInt(cmd.args[0].substring(1, cmd.args[0].indexOf(')')), 16)];
                    t1 = 1;
                    break;
                default:
                    n1 = Integer.parseInt(cmd.args[0], 16);
                    break;
            }

            if (cmd.argcount == 2) {
                switch (cmd.args[1].charAt(0)) {
                    case '#':
                        n2 = Integer.parseInt(cmd.args[1].substring(1), 16);
                        t2 = -1;
                        break;
                    case '(':
                        n2 = memory[Integer.parseInt(cmd.args[1].substring(1, cmd.args[1].indexOf(')')), 16)];
                        t2 = 1;
                        break;
                    default:
                        n2 = Integer.parseInt(cmd.args[1], 16);
                        break;
                }
            }
        }

        switch (cmd.command) {
            case 1:
                if (cmd.argcount < 2) {
                    System.out.println(bytesToHex(memory[n1]));
                } else {
                    n2++;
                    while(n1 < n2) {
                        System.out.print(bytesToHex(memory[n1++]));
                        System.out.print(' ');
                    }
                    System.out.println();
                }
                break;
            case 2:
                if (t1 == 0) {
                    memory[n2] = memory[n1]; // maybe not true description todo
                } else {
                    memory[n2] = n1;
                }
                break;
            case 3:
                if (t1 < 0) {
                    memory[n2] = (memory[n2] + n1) % 256;
                } else {
                    memory[n2] = (memory[n2] + memory[n1]) % 256;
                }
                break;
            case 4:
                if (t1 < 0) {
                    memory[n2] =  (memory[n2] - n1) % 256;
                } else {
                    memory[n2] = (memory[n2] - memory[n1]) % 256; // todo maybe not
                }
                break;
            case 5:
                if (t1 < 0) {
                    memory[n2] = (memory[n2] & n1) % 256;
                } else {
                    memory[n2] = (memory[n2] & memory[n1]) % 256;
                }
                break;
            case 6:
                if (t1 < 0) {
                    memory[n2] = (memory[n2] | n1) % 256;
                } else {
                    memory[n2] = (memory[n2] | memory[n1]) % 256;
                }
                break;
            case 7:
                if (t1 < 0) {
                    memory[n2] = (memory[n2] ^ n1) % 256;
                } else {
                    memory[n2] = (memory[n2] ^ memory[n1]) % 256;
                }
                break;
            case 8:
                if (t1 < 0) {
                    lastCMPR = Integer.compare(n1, memory[n2]);
                } else {
                    lastCMPR = Integer.compare(memory[n1], memory[n2]);
                }
                break;
            case 9: // BEQ
                if (lastCMPR == 0) {
                    i = labels.get(cmd.args[0]) - 1;
                }
                break;
            case 10: // BNE
                if (lastCMPR != 0 && lastCMPR > -2) {
                    i = labels.get(cmd.args[0]) - 1;
                }
                break;
            case 11: // BGT
                if (lastCMPR == 1) {
                    i = labels.get(cmd.args[0]) - 1;
                }
                break;
            case 12: // BLT
                if (lastCMPR == -1) {
                    i = labels.get(cmd.args[0]) - 1;
                }
                break;
            case 13: // BGE
                if (lastCMPR == 1 || lastCMPR == 0) {
                    i = labels.get(cmd.args[0]) - 1;
                }
                break;
            case 14: // BLE
                if (lastCMPR == -1 || lastCMPR == 0) {
                    i = labels.get(cmd.args[0]) - 1;
                }
                break;
        }
        i++;
    }

    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(int bytes) {
        char[] hexChars = new char[2];
        int v = bytes & 0xFF;
        hexChars[0] = hexArray[v >>> 4];
        hexChars[1] = hexArray[v & 0x0F];
        return new String(hexChars);
    }

    static int command(String command) {

        switch (command) {

            case "PRINT":
                return 1;
            case "MOVE":
                return 2;
            case "ADD":
                return 3;
            case "SUB":
                return 4;
            case "AND":
                return 5;
            case "OR":
                return 6;
            case "XOR":
                return 7;
            case "COMP":
                return 8;
            case "BEQ":
                return 9;
            case "BNE":
                return 10;
            case "BGT":
                return 11;
            case "BLT":
                return 12;
            case "BGE":
                return 13;
            case "BLE":
                return 14;
            default:
                return 0;
        }
    }
}

class Command{

    int command;
    String[] args;
    int argcount;

    Command(int cmd, String[] arg) {
        command = cmd;
        argcount = arg.length;
        args = arg;
    }
}

