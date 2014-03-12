/**
 * 
 */
package com.acl.qa.taf.util.djnativeswing.api;

/**
 * Script Name   : <b>JIconButton.java</b>
 * Generated     : <b>12:15:59 AM</b> 
 * Description   : <b>ACL Test Automation</b>
 * 
 * @since  Aug 23, 2013
 * @author steven_xiang
 * 
 */
import javax.swing.*;

/** A regular JButton created with an ImageIcon and with borders
 *  and content areas turned off.
 *  1998 Marty Hall, http://www.apl.jhu.edu/~hall/java/
 */

public class JIconButton extends JButton {
  public JIconButton(String file) {
    super(new ImageIcon(file));
    setContentAreaFilled(false);
    setBorderPainted(false);
    setFocusPainted(false);
  }
}
