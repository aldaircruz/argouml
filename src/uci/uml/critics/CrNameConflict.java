// Copyright (c) 1995, 1996 Regents of the University of California.
// All rights reserved.
//
// This software was developed by the Arcadia project
// at the University of California, Irvine.
//
// Redistribution and use in source and binary forms are permitted
// provided that the above copyright notice and this paragraph are
// duplicated in all such forms and that any documentation,
// advertising materials, and other materials related to such
// distribution and use acknowledge that the software was developed
// by the University of California, Irvine.  The name of the
// University may not be used to endorse or promote products derived
// from this software without specific prior written permission.
// THIS SOFTWARE IS PROVIDED ``AS IS'' AND WITHOUT ANY EXPRESS OR
// IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED
// WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.

// File: CrNameConflict.java.java
// Classes: CrNameConflict.java
// Original Author: jrobbins@ics.uci.edu
// $Id$

package uci.uml.critics;

import java.util.*;
import uci.argo.kernel.*;
import uci.util.*;
import uci.uml.Foundation.Core.*;
import uci.uml.Foundation.Data_Types.*;
import uci.uml.Model_Management.*;

/** Well-formedness rule [1] for Namespace. See page 33 of UML 1.1
 *  Semantics. OMG document ad/97-08-04. */

public class CrNameConflict extends CrUML {

  public CrNameConflict() {
    setHeadline("Name conflict");
    sd("Every element of a namespace must have a unique name. \n\n"+
       "Clear and unambiguous naming is key to code generation and \n"+
       "the understandability and maintainability of the design. \n\n"+
       "To fix this, use the FixIt button, or manually select the elements \n"+
       "and use the Properties tab to change their names.");
    addSupportedDecision(CrUML.decNAMING);
  }

  protected void sd(String s) { setDescription(s); }
  
  public boolean predicate(Object dm, Designer dsgr) {
    if (!(dm instanceof Namespace)) return NO_PROBLEM;
    Namespace ns = (Namespace) dm;
    Vector oes = ns.getOwnedElement();
    if (oes == null) return NO_PROBLEM;
    Vector namesSeen = new Vector();
    java.util.Enumeration enum = oes.elements();
    while (enum.hasMoreElements()) {
      ElementOwnership eo = (ElementOwnership) enum.nextElement();
      ModelElement me = (ModelElement) eo.getModelElement();
      if (me instanceof Association) continue;
      if (me instanceof Generalization) continue;
      Name meName = me.getName();
      if (meName == null || meName.equals(Name.UNSPEC)) continue;
      if (namesSeen.contains(meName.getBody())) return PROBLEM_FOUND;
      namesSeen.addElement(meName.getBody());
    }
    return NO_PROBLEM;
  }

} /* end class CrNameConflict.java */

