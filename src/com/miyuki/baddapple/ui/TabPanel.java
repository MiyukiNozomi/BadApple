package com.miyuki.baddapple.ui;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.miyuki.baddapple.Resource;
import com.miyuki.baddapple.Theme;
import com.miyuki.baddapple.WelcomePage;

public class TabPanel extends JPanel {
	private static final long serialVersionUID = 688529252359L;
	
	public JTabbedPane tabbedPanel;
	
	public TabPanel() {
		setLayout(new BorderLayout());
		setBackground(Theme.GetColor("main-background"));
		
		tabbedPanel = new UITabbedPane();
		tabbedPanel.setBackground(getBackground());
		tabbedPanel.setBorder(BorderFactory.createEmptyBorder());
		
		tabbedPanel.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int saveI = tabbedPanel.getSelectedIndex();

				if (saveI == -1) {
					return;
				}

				for (int i = 0; i < tabbedPanel.getTabCount(); i++) {
					if (tabbedPanel.getTabComponentAt(i) == null)
						return;
					((TabCompView) tabbedPanel.getTabComponentAt(i)).onHide();
				}

				((TabCompView) tabbedPanel.getTabComponentAt(saveI)).onShown();
			}
		});
		
		tabbedPanel.addTab("WelcomePage", Resource.GetImage("internal://tray/whiteicon.png"), new WelcomePage());
		add(tabbedPanel, BorderLayout.CENTER);
	}
}
