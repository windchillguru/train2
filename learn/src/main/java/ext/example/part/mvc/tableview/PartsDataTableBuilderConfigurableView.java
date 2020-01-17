package ext.example.part.mvc.tableview;

import com.ptc.core.components.descriptor.DescriptorConstants.ColumnIdentifiers;
import com.ptc.core.htmlcomp.components.JCAConfigurableTable;
import com.ptc.core.htmlcomp.createtableview.Attribute.TextAttribute;
import com.ptc.core.htmlcomp.tableview.AbstractConfigurableTable;
import com.ptc.core.htmlcomp.tableview.SortColumnDescriptor;
import com.ptc.core.htmlcomp.tableview.TableColumnDefinition;
import com.ptc.core.htmlcomp.tableview.TableViewDescriptor;
import com.ptc.mvc.util.ClientMessageSource;
import ext.example.part.tableResource;
import wt.lifecycle.LifeCycleManaged;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.util.WTContext;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

public class PartsDataTableBuilderConfigurableView extends JCAConfigurableTable {

	private final ClientMessageSource messageSource = getMessageSource(tableResource.class.getName());
	private static final String RESOURCES =tableResource.class.getName();
	Locale locale = WTContext.getContext().getLocale();		
	
	public PartsDataTableBuilderConfigurableView() {
		super();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class[] getClassTypes() {
		return new Class[] {WTPart.class};
	}

	@Override
	public String getDefaultSortColumn() {
		return WTPartMaster.NUMBER;
	}

	@Override
	public String getLabel(Locale arg0) {
		return messageSource.getMessage(tableResource.TABLE_LABEL);
	}

	@Override
	public String getOOTBActiveViewName() {
		return messageSource.getMessage(tableResource.TABLE_NAME);
	}

	@Override
	public List<TableViewDescriptor> getOOTBTableViews(String param, Locale locale) throws WTException {
		ArrayList<TableViewDescriptor> ootbTableViewList = new ArrayList<TableViewDescriptor>();
		Vector<TableColumnDefinition> vector = new Vector<TableColumnDefinition>();
		try {			
			vector.add(TableColumnDefinition.newTableColumnDefinition(WTPartMaster.NUMBER, true));
			vector.add(TableColumnDefinition.newTableColumnDefinition(WTPartMaster.NAME, true));
			vector.add(TableColumnDefinition.newTableColumnDefinition("SCA|displayType", true));
			vector.add(TableColumnDefinition.newTableColumnDefinition(ColumnIdentifiers.INFO_ACTION, true));
			vector.add(TableColumnDefinition.newTableColumnDefinition(LifeCycleManaged.LIFE_CYCLE_STATE, true));
			 
			ArrayList<SortColumnDescriptor> sortColumnDescriptorList = new  ArrayList<SortColumnDescriptor>();
			SortColumnDescriptor sortColumnDescriptor = new SortColumnDescriptor();
			sortColumnDescriptor.setColumnId(WTPartMaster.NUMBER);
			sortColumnDescriptor.setOrder("ASCENDING");
			sortColumnDescriptorList.add(sortColumnDescriptor);

			String defaultValue=AbstractConfigurableTable.getViewResourceEntryKey(tableResource.class.getName(), tableResource.TABLE_NAME);
			// Default Table View 
			TableViewDescriptor tableViewDescriptor = TableViewDescriptor.newTableViewDescriptor(
					defaultValue, param, true, true, vector, null, true,defaultValue);
			tableViewDescriptor.setColumnSortOrder(sortColumnDescriptorList);
			ootbTableViewList.add(tableViewDescriptor);
		} catch (WTPropertyVetoException e) {
			e.printStackTrace();
			throw new WTException(e);
		}

		return ootbTableViewList;
	}
	
	@Override
	public List<TextAttribute> getSpecialTableColumnsAttrDefinition(Locale arg0) {
	    ArrayList<TextAttribute> localArrayList = new ArrayList<TextAttribute>(1);
	    return localArrayList;
	}
	

}
