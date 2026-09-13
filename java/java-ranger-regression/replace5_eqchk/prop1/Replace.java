public class Replace {
	
  static class ReplaceState {
	  public int patParaIndex = 0;
	  public int patIndex = 0;
	  public int subParaIndex = 0;
	  public int subIndex = 0;
	  public int strIndex = 0;
	  public int tempIndex = 0;
	  int printBufIdx = 0;
	  char[] printBuf;
  }
  
  // original non-final static vars are moved to this state-obj:
  public ReplaceState replstate = new ReplaceState() ;

  public final int patParaLen = 3;
  //public int patParaIndex = 0;
  public final int patLen = 10;
  //public int patIndex = 0;

  public final int subParaLen = 3;
  //public int subParaIndex = 0;
  public final int subLen = 10;
  //public int subIndex = 0;

  public final int strLen = 2;
  //public int strIndex = 0;
  //public int tempIndex = 0;

  public final char ENDSTR = '\0';
  public final char ESCAPE = '@';
  public final char CLOSURE = '*';
  public final char BOL = '%';
  public final char EOL = '$';
  public final char ANY = '?';
  public final char CCL = '[';
  public final char CCLEND = ']';
  public final char NEGATE = '^';
  public final char NCCL = '!';
  public final char LITCHAR = 'c';
  public final char DASH = '-';

  public final int DITTO = -1;
  public final int TAB = 9;
  public final int NEWLINE = 10;
  public final int CLOSIZE = 1;

  public final int printBufLen = 50 ;

  //int printBufIdx = 0;
  //char[] printBuf;

  public void reset() {
	replstate.patParaIndex = 0;
	replstate.patIndex = 0;
	replstate.subParaIndex = 0;
	replstate.subIndex = 0;
	replstate.strIndex = 0;
	replstate.tempIndex = 0;
	replstate.printBufIdx = 0;
  }

  public char[] mainProcess(char i0, char i1, char i2, char i3, char i4) {
	replstate.printBuf = new char[printBufLen];

    char[] patPara = new char[patParaLen];
    patPara[0] = i0;
    patPara[1] = i1;

    patPara[2] = '\0';
    char[] pat = new char[patLen];
    int patResult = makepat(patPara, pat);
    if (patResult <= 0) {
      System.out.println("Challege: illegal pattern!");
      return new char[] {};
    }

    char[] subPara = new char[subParaLen];
    subPara[0] = i2;
    subPara[1] = i3;
    subPara[2] = '\0';
    char[] sub = new char[subLen];
    int subResult = makesub(subPara, sub);
    if (subResult <= 0) {
      System.out.println("Challege: illegal sub");
    }

    char[] str = new char[strLen];
    str[0] = i4;

    str[1] = '\0';

    change(str, pat, sub);
    for (int i = 0; i < replstate.printBuf.length; i++) replstate.printBuf[i] = '0';
    char[] retChar = new char[pat.length + sub.length + str.length + replstate.printBuf.length];
    for (int i = 0; i < replstate.printBuf.length; i++) replstate.printBuf[i] = '0';
    int outIndex = 0;
    for (int i = 0; i < pat.length; i++) retChar[outIndex++] = pat[i];
    for (int i = 0; i < sub.length; i++) retChar[outIndex++] = sub[i];
    for (int i = 0; i < str.length; i++) retChar[outIndex++] = str[i];
    for (int i = 0; i < printBufLen; i++) retChar[outIndex++] = replstate.printBuf[i];

    return retChar;
  }

  private void change(char[] lin, char[] pat, char[] sub) {

    int lastm = -1;
    int i = 0;

    while (lin[i] != ENDSTR) {
    	replstate.strIndex = i;

      int m = amatch(lin, pat, 0);
      if (m >= 0) {
        if (lastm != m) {
          putsub(lin, i, m, sub);
          lastm = m;
        }
      }
      if (m == -1) {
        System.out.print(lin[i]);
        if (replstate.printBufIdx < replstate.printBuf.length) 
        	replstate.printBuf[replstate.printBufIdx++] = lin[i];
        i = i + 1;
      } else if (m == i) {
        System.out.print(lin[i]);
        if (replstate.printBufIdx < replstate.printBuf.length) 
        	replstate.printBuf[replstate.printBufIdx++] = lin[i];
        i = i + 1;
      } else {
        i = m;
      }
    }
  }

  private void putsub(char[] lin, int s1, int s2, char[] sub) {
    int i = 0;
    while (sub[i] != ENDSTR) {
      char ch = (char) DITTO;
      if (sub[i] == ch) {
        System.out.print(lin[i]);
        if (replstate.printBufIdx < replstate.printBuf.length) 
        	replstate.printBuf[replstate.printBufIdx++] = lin[i];
      } else {
        System.out.print(sub[i]);
        if (replstate.printBufIdx < replstate.printBuf.length) 
        	replstate.printBuf[replstate.printBufIdx++] = sub[i];
      }
      i = i + 1;
    }
  }

  private int amatch(char[] lin, char[] pat, int j) {
    int k = -1;
    boolean done = false;
    boolean continueIndex = false;
    if (pat[j] != ENDSTR) {
      continueIndex = true;
    }
    while (continueIndex) {
      if (pat[j] == CLOSURE) {
        int _patsize = patsize(pat, j);
        j = j + _patsize;
        replstate.tempIndex = replstate.strIndex;
        continueIndex = false;
        if (lin[replstate.tempIndex] != ENDSTR) {
          continueIndex = true;
        }
        while (continueIndex) {
          boolean result = omatch(lin, pat, j);
          if (!result) {
            done = true;
          }
          continueIndex = false;
          if (!done) {
            if (lin[replstate.tempIndex] != ENDSTR) {
              continueIndex = true;
            }
          }
        }

        done = false;
        continueIndex = false;
        int offset = replstate.strIndex;
        replstate.strIndex = replstate.tempIndex;
        if (replstate.strIndex >= offset) {
          continueIndex = true;
        }

        while (continueIndex) {
          _patsize = patsize(pat, j);
          _patsize = _patsize + j;
          k = amatch(lin, pat, _patsize);
          if (k >= 0) {
            done = true;
          } else {
        	  replstate.strIndex = replstate.strIndex - 1;
          }
          continueIndex = false;
          if (!done) {
            if (replstate.strIndex > offset) {
              continueIndex = true;
            }
          }
        }
        replstate.strIndex = k;
        done = true;
      } else {
    	replstate.tempIndex = replstate.strIndex;
        boolean result = omatch(lin, pat, j);
        replstate.strIndex = replstate.tempIndex;
        if (!result) {
        	replstate.strIndex = -1;
          done = true;
        } else {
          int _patsize = patsize(pat, j);
          j = j + _patsize;
        }
      }
      continueIndex = false;
      if (!done) {
        if (pat[j] != ENDSTR) {
          continueIndex = true;
        }
      }
    }
    return replstate.strIndex;
  }

  private boolean omatch(char[] lin, char[] pat, int j) {
    boolean result = false;

    int advance = -1;
    if (lin[replstate.tempIndex] == ENDSTR) {
      result = false;
    } else {
      char tempChar = pat[j];
      boolean _in_pat_set = in_pat_set(tempChar);
      if (!_in_pat_set) {
        System.out.println("In omatch: can't happen!");
      } else {
        if (pat[j] == LITCHAR) {
          if (lin[replstate.tempIndex] == pat[j + 1]) {
            advance = 1;
          }
        } else if (pat[j] == BOL) {
          if (replstate.tempIndex == 0) {
            advance = 0;
          }
        } else if (pat[j] == ANY) {
          if (lin[replstate.tempIndex] != NEWLINE) {
            advance = 1;
          }
        } else if (pat[j] == EOL) {
          if (lin[replstate.tempIndex] == NEWLINE) {
            advance = 0;
          }
        } else if (pat[j] == CCL) {
          int _j = j + 1;
          char tempChar1 = lin[replstate.tempIndex];
          boolean _locate = locate(tempChar1, pat, _j);
          if (_locate) {
            advance = 1;
          }
        } else if (pat[j] == NCCL) {
          if (lin[replstate.tempIndex] != NEWLINE) {
            int _j = j + 1;
            char tempChar1 = lin[replstate.tempIndex];
            boolean _locate = locate(tempChar1, pat, _j);
            if (!_locate) {
              advance = 1;
            }
          }
        } else {
          CaseError(pat[j]);
        }
      }
    }
    if (advance >= 0) {
      replstate.tempIndex = replstate.tempIndex + advance;
      result = true;
    } else {
      result = false;
    }
    return result;
  }

  private boolean locate(char c, char[] pat, int offset) {
    boolean flag = false;

    int i = offset + pat[offset];
    while (i > offset) {
      if (c == pat[i]) {
        flag = true;
        i = offset;
      } else {
        i = i - 1;
      }
    }
    return flag;
  }

  private int patsize(char[] pat, int n) {
    char tempChar = pat[n];
    boolean _in_pat_set = in_pat_set(tempChar);
    int size = -1;
    if (!_in_pat_set) {
      System.out.println("In patsize: can't happen");
    } else {
      if (pat[n] == LITCHAR) {
        size = 2;
      } else if (pat[n] == BOL) {
        size = 1;
      } else if (pat[n] == EOL) {
        size = 1;
      } else if (pat[n] == ANY) {
        size = 1;
      } else if (pat[n] == CCL) {
        size = pat[n + 1] + 2;
      } else if (pat[n] == NCCL) {
        size = pat[n + 1] + 2;
      } else if (pat[n] == CLOSURE) {
        size = CLOSIZE;
      } else {
        CaseError(pat[n]);
      }
    }
    return size;
  }

  private void CaseError(char c) {
    System.out.println("Missing case limb!");
  }

  private boolean in_pat_set(char ch) {
    if (ch == LITCHAR) {
      return true;
    } else if (ch == BOL) {
      return true;
    } else if (ch == EOL) {
      return true;
    } else if (ch == ANY) {
      return true;
    } else if (ch == CCL) {
      return true;
    } else if (ch == NCCL) {
      return true;
    } else if (ch == CLOSURE) {
      return true;
    } else {
      return false;
    }
  }

  private int makesub(char[] arg, char[] sub) {
    boolean continueIndex = false;
    if (arg[replstate.subParaIndex] != ENDSTR) {
      continueIndex = true;
    }
    while (continueIndex) {
      if (arg[replstate.subParaIndex] == '&') {
        char ch = (char) DITTO;
        if (replstate.subIndex < subLen) {
          sub[replstate.subIndex] = ch;
          replstate.subIndex = replstate.subIndex + 1;
        }
      } else {
        char escjunk = escSub(arg);
        if (replstate.subIndex < subLen) {
          sub[replstate.subIndex] = escjunk;
          replstate.subIndex = replstate.subIndex + 1;
        }
      }
      replstate.subParaIndex = replstate.subParaIndex + 1;
      continueIndex = false;
      if (arg[replstate.subParaIndex] != ENDSTR) {
        continueIndex = true;
      }
    }
    int result = 0;
    if (arg[replstate.subParaIndex] != ENDSTR) {
      result = 0;
    } else {
      if (replstate.subIndex < subLen) {
        sub[replstate.subIndex] = ENDSTR;
        result = replstate.subParaIndex;
      } else {
        result = 0;
      }
    }
    return result;
  }

  private int makepat(char[] arg, char[] pat) {
    int lastj = 0;
    boolean done = false;
    boolean continueIndex = false;
    if (arg[replstate.patParaIndex] != ENDSTR) {
      continueIndex = true;
    }

    while (continueIndex) {
      int lj = replstate.patIndex;
      if (arg[replstate.patParaIndex] == ANY) {
        if (replstate.patIndex < patLen) {
          pat[replstate.patIndex] = ANY;
          replstate.patIndex = replstate.patIndex + 1;
        }
      } else if (arg[replstate.patParaIndex] == CCL) {
        boolean getres = getccl(arg, pat);

        if (!getres) {
          done = true;
        } else {
          done = false;
        }
      } else if (arg[replstate.patParaIndex] == BOL) {
        if (replstate.patParaIndex == 0) {
          if (replstate.patIndex < patLen) {
            pat[replstate.patIndex] = BOL;
            replstate.patIndex = replstate.patIndex + 1;
          }
        } else {
          if (replstate.patIndex < patLen) {
            pat[replstate.patIndex] = LITCHAR;
            replstate.patIndex = replstate.patIndex + 1;
          }

          char escjunk = esc(arg);
          if (replstate.patIndex < patLen) {
            pat[replstate.patIndex] = escjunk;
            replstate.patIndex = replstate.patIndex + 1;
          }
        }
      } else if (arg[replstate.patParaIndex] == CLOSURE) {
        if (replstate.patParaIndex > 0) {
          lj = lastj;
          char tempChar = pat[lj];
          boolean _inset = in_set_2(tempChar);
          if (_inset) {
            done = true;
          } else {
            stclose(pat, lastj);
          }
        } else {
          if (replstate.patIndex < patLen) {
            pat[replstate.patIndex] = LITCHAR;
            replstate.patIndex = replstate.patIndex + 1;
          }

          char escjunk = esc(arg);
          if (replstate.patIndex < patLen) {
            pat[replstate.patIndex] = escjunk;
            replstate.patIndex = replstate.patIndex + 1;
          }
        }
      } else if (arg[replstate.patParaIndex] == EOL) {
        if (arg[replstate.patParaIndex + 1] == ENDSTR) {
          if (replstate.patIndex < patLen) {
            pat[replstate.patIndex] = EOL;
            replstate.patIndex = replstate.patIndex + 1;
          }
        } else {
          if (replstate.patIndex < patLen) {
            pat[replstate.patIndex] = LITCHAR;
            replstate.patIndex = replstate.patIndex + 1;
          }

          char escjunk = esc(arg);
          if (replstate.patIndex < patLen) {
            pat[replstate.patIndex] = escjunk;
            replstate.patIndex = replstate.patIndex + 1;
          }
        }
      } else {
        if (replstate.patIndex < patLen) {
          pat[replstate.patIndex] = LITCHAR;
          replstate.patIndex = replstate.patIndex + 1;
        }

        char escjunk = esc(arg);
        if (replstate.patIndex < patLen) {
          pat[replstate.patIndex] = escjunk;
          replstate.patIndex = replstate.patIndex + 1;
        }
      }
      lastj = lj;

      continueIndex = false;
      if (!done) {
        replstate.patParaIndex = replstate.patParaIndex + 1;
        if (arg[replstate.patParaIndex] != ENDSTR) {
          continueIndex = true;
        }
      }
    }

    int result = 1;
    if (replstate.patIndex < patLen) {
      pat[replstate.patIndex] = ENDSTR;
    } else {
      result = 0;
    }
    if (done) {
      result = 0;
    } else if (arg[replstate.patParaIndex] != ENDSTR) {
      result = 0;
    }
    return result;
  }

  private void stclose(char[] pat, int lastj) {
    for (int jp = replstate.patIndex - 1; jp >= lastj; ) {
      int jt = jp + CLOSIZE;
      if (jt < patLen) {
        pat[jt] = pat[jp];
      }
      jp = jp - 1;
    }
    replstate.patIndex = replstate.patIndex + CLOSIZE;
    pat[lastj] = CLOSURE;
  }

  private boolean in_set_2(char ch) {
    if (ch == BOL) {
      return true;
    } else if (ch == EOL) {
      return true;
    } else if (ch == CLOSURE) {
      return true;
    } else {
      return false;
    }
  }

  private char escSub(char[] s) {
    char result;
    if (s[replstate.subParaIndex] != ESCAPE) {
      result = s[replstate.subParaIndex];
    } else if (s[replstate.subParaIndex + 1] == ENDSTR) {
      result = ESCAPE;
    } else {
      replstate.subParaIndex = replstate.subParaIndex + 1;
      if (s[replstate.subParaIndex] == 'n') {
        result = (char) NEWLINE;
      } else if (s[replstate.subParaIndex] == 't') {
        result = (char) TAB;
      } else {
        result = s[replstate.subParaIndex];
      }
    }
    return result;
  }

  private char esc(char[] s) {
    char result;
    if (s[replstate.patParaIndex] != ESCAPE) {
      result = s[replstate.patParaIndex];
    } else if (s[replstate.patParaIndex + 1] == ENDSTR) {
      result = ESCAPE;
    } else {
      replstate.patParaIndex = replstate.patParaIndex + 1;
      if (s[replstate.patParaIndex] == 'n') {
        result = (char) NEWLINE;
      } else if (s[replstate.patParaIndex] == 't') {
        result = (char) TAB;
      } else {
        result = s[replstate.patParaIndex];
      }
    }
    return result;
  }

  private boolean getccl(char[] arg, char[] pat) {
    replstate.patParaIndex = replstate.patParaIndex + 1;
    if (arg[replstate.patParaIndex] == NEGATE) {
      if (replstate.patIndex < patLen) {
        pat[replstate.patIndex] = NCCL;
        replstate.patIndex = replstate.patIndex + 1;
      }
      replstate.patParaIndex = replstate.patParaIndex + 1;
    } else {
      if (replstate.patIndex < patLen) {
        pat[replstate.patIndex] = CCL;
        replstate.patIndex = replstate.patIndex + 1;
      }
    }

    int jstart = replstate.patIndex;
    if (replstate.patIndex < patLen) {
      pat[replstate.patIndex] = '\0';
      replstate.patIndex = replstate.patIndex + 1;
    }

    dodash(CCLEND, arg, pat);

    char _jstart = (char) (replstate.patIndex - jstart - 1);
    pat[jstart] = _jstart;
    if (arg[replstate.patParaIndex] == CCLEND) {
      return true;
    } else {
      return false;
    }
  }

  private void dodash(char delim, char[] src, char[] dest) {

    boolean continueIndex = false;
    if (src[replstate.patParaIndex] != delim) {
      if (src[replstate.patParaIndex] != ENDSTR) {
        continueIndex = true;
      }
    }
    while (continueIndex) {
      if (src[replstate.patParaIndex] != DASH) {
        if (replstate.patIndex < patLen) {
          dest[replstate.patIndex] = src[replstate.patParaIndex];
          replstate.patIndex = replstate.patIndex + 1;
        }
      } else if (replstate.patIndex <= 1) {
        if (replstate.patIndex < patLen) {
          dest[replstate.patIndex] = DASH;
          replstate.patIndex = replstate.patIndex + 1;
        }
      } else if (src[replstate.patParaIndex + 1] == ENDSTR) {
        if (replstate.patIndex < patLen) {
          dest[replstate.patIndex] = DASH;
          replstate.patIndex = replstate.patIndex + 1;
        }
      } else {
        char tempChar1 = src[replstate.patParaIndex - 1];
        boolean _isNum_0 = isalnum(tempChar1);
        if (_isNum_0) {
          char tempChar2 = src[replstate.patParaIndex + 1];
          boolean _isNum_1 = isalnum(tempChar2);
          if (_isNum_1) {
            if (src[replstate.patParaIndex - 1] <= src[replstate.patParaIndex + 1]) {
              for (int k = src[replstate.patParaIndex - 1] + 1; k <= src[replstate.patParaIndex + 1]; ) {
                char _k = (char) k;
                if (replstate.patIndex < patLen) {
                  dest[replstate.patIndex] = _k;
                  replstate.patIndex = replstate.patIndex + 1;
                }
                k = k + 1;
              }
              replstate.patParaIndex = replstate.patParaIndex + 1;
            } else {
              if (replstate.patIndex < patLen) {
                dest[replstate.patIndex] = DASH;
                replstate.patIndex = replstate.patIndex + 1;
              }
            }
          } else {
            if (replstate.patIndex < patLen) {
              dest[replstate.patIndex] = DASH;
              replstate.patIndex = replstate.patIndex + 1;
            }
          }
        } else {
          if (replstate.patIndex < patLen) {
            dest[replstate.patIndex] = DASH;
            replstate.patIndex = replstate.patIndex + 1;
          }
        }
      }
      replstate.patParaIndex = replstate.patParaIndex + 1;
      continueIndex = false;
      if (src[replstate.patParaIndex] != delim) {
        if (src[replstate.patParaIndex] != ENDSTR) {
          continueIndex = true;
        }
      }
    }
  }

  private boolean isalnum(char ch) {
    if (ch >= '0') {
      if (ch <= '9') {
        return true;
      } else if (ch >= 'A') {
        if (ch <= 'Z') {
          return true;
        } else if (ch >= 'a') {
          if (ch <= 'z') {
            return true;
          }
        }
      }
    }
    return false;
  }
}
