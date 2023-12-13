package org.jcubitainer.display.table;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;

import org.jcubitainer.tools.Messages;

public class NetworkDisplayTable extends JPanel {

    private SimpleTableModel model = null;

    private String title = Messages
            .getString("NetworkDisplayTable.joueurs_en_ligne"); //$NON-NLS-1$

    public NetworkDisplayTable(int largeur, int hauteur) {

        super(new GridLayout(1, 0));

        setBackground(Color.black);
        setForeground(Color.black);

        model = new SimpleTableModel();

        JTable table = new JTable(model);
        table
                .setPreferredScrollableViewportSize(new Dimension(largeur,
                        hauteur));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setBackground(Color.black);
        table.setForeground(Color.red);
        table.setSelectionForeground(Color.yellow);
        table.setSelectionBackground(Color.darkGray);
        table.setCellSelectionEnabled(false);
        table.setGridColor(Color.black);
        table.setShowGrid(false);
        table.getTableHeader().setFont(new Font("Courier", Font.BOLD, 13)); //$NON-NLS-1$
        table.getTableHeader().setBackground(Color.black);
        table.getTableHeader().setForeground(Color.yellow);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.setFont(new Font("Helvetica", Font.BOLD, 12)); //$NON-NLS-1$

        table.setRequestFocusEnabled(false);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.black);
        scrollPane.setBackground(Color.black);
        JScrollBar bar = new JScrollBar();
        bar.setBackground(Color.black);
        bar.setForeground(Color.red);
        scrollPane.setVerticalScrollBar(bar);
        add(scrollPane);
        setOpaque(true);

    }

    protected class SimpleTableModel extends AbstractTableModel {

        private String[] columnNames = { title };

        private ArrayList data = new ArrayList();

        public void setDatas(ArrayList list) {
            data = list;
            fireTableDataChanged();
        }

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return data.size();
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return data.get(row);
        }

        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        public boolean isCellEditable(int row, int col) {
            return false;
        }

        public void add(Comparable value) {
            synchronized (data) {
                remove(value);
                int size = data.size();
                int i = 0;
                for (; i < size; i++) {
                    Comparable o = (Comparable) data.get(i);
                    if (o.compareTo(value) > 0)
                        break;
                }
                data.add(i, value);
                fireTableRowsInserted(i, data.size());
            }
        }

        public void remove(Comparable value) {
            synchronized (data) {
                int pos = data.indexOf(value);
                data.remove(value);
                fireTableRowsDeleted(pos, data.size());
            }
        }

    }

    public void addData(Comparable o) {
        model.add(o);
    }

    public void removeData(Comparable o) {
        model.remove(o);
    }

}