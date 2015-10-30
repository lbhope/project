package com.example.customui.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.customui.R;
/**
 * 定义动态控件，支持点击最后一个按钮添加一行控件，否则删除当前选中的该行控件
 * @author libin
 *
 */
public class DynamicControl extends LinearLayout {
	private LayoutInflater mInflater;
	private TableLayout layout;
	private int tableRowID;
	private int dynamicBtnID;
	private int dynamicPreID;
	private int dynamicAftID;

	private List<View> idList = new ArrayList<View>();

	/** * @param context */
	public DynamicControl(Context context) {
		this(context, null);
	}

	public DynamicControl(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mInflater = LayoutInflater.from(context);
		layout = (TableLayout) mInflater.inflate(R.layout.dynamcontrol, null); // 设置默认值
		View view = layout.findViewById(R.id.dynamic_btn_1);
		view.setOnClickListener(getClickListener());
		idList.add(view);
		tableRowID = R.id.dynamic_row_1;
		dynamicBtnID = R.id.dynamic_btn_1;
		dynamicPreID = R.id.dynamic_pre_1;
		dynamicAftID = R.id.dynamic_aft_1;
		addView(layout);
	}

	private OnClickListener getClickListener() {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				clickLogic(v);
			}
		};
	}

	private void clickLogic(View v) {
		if (!idList.isEmpty()) {
			if (v == idList.get(idList.size() - 1)) {
				addControls(v);
			} else {
				removeControls(v);
			}
		}
	}

	private TableRow getNewRow() {
		TableRow row = getDefaultTableRow();
		row.addView(getDefaultImageButton());
		row.addView(getDefaultPrevEditText());
		row.addView(getDefaultTextView());
		row.addView(getDefaultAfterEditText());
		return row;
	}

	private void addControls(View v) {
		layout.addView(getNewRow());
		((ImageButton) v).setImageResource(R.drawable.home);
	}

	private void removeControls(View v) {
		if (idList.size() <= 1) {
			return;
		}
		TableRow parent = (TableRow) v.getParent();
		((TableLayout) parent.getParent()).removeView(parent);
		idList.remove(v);
	}

	private TableRow getDefaultTableRow() {
		tableRowID++;
		TableRow row = new TableRow(getContext());
		android.widget.TableRow.LayoutParams params = new android.widget.TableRow.LayoutParams();
		params.width = android.widget.TableRow.LayoutParams.WRAP_CONTENT;
		params.height = android.widget.TableRow.LayoutParams.WRAP_CONTENT;
		row.setLayoutParams(params);
		row.setGravity(Gravity.CENTER);
		row.setId(tableRowID);
		return row;
	}

	private EditText getDefaultEditText() {
		EditText text = new EditText(getContext());
		android.widget.TableRow.LayoutParams params = new android.widget.TableRow.LayoutParams();
		params.width = android.widget.TableRow.LayoutParams.WRAP_CONTENT;
		params.height = android.widget.TableRow.LayoutParams.WRAP_CONTENT;
		text.setLayoutParams(params);
		// text.set
		text.setMaxLines(1);
		text.setWidth(80);
		return text;
	}

	private EditText getDefaultPrevEditText() {
		dynamicPreID++;
		EditText text = getDefaultEditText();
		text.setId(dynamicPreID);
		return text;
	}

	private EditText getDefaultAfterEditText() {
		dynamicAftID++;
		EditText text = getDefaultEditText();
		text.setId(dynamicAftID);
		return text;

	}

	private TextView getDefaultTextView() {
		TextView view = new TextView(getContext());
		android.widget.TableRow.LayoutParams params = new android.widget.TableRow.LayoutParams();
		params.width = android.widget.TableRow.LayoutParams.WRAP_CONTENT;
		params.height = android.widget.TableRow.LayoutParams.WRAP_CONTENT;
		view.setLayoutParams(params);
		view.setText("-");
		return view;
	}

	private ImageButton getDefaultImageButton() {
		dynamicBtnID++;
		ImageButton btn = new ImageButton(getContext());
		btn.setAdjustViewBounds(true);
		android.widget.TableRow.LayoutParams params = new android.widget.TableRow.LayoutParams();
		params.width = android.widget.TableRow.LayoutParams.WRAP_CONTENT;
		params.height = android.widget.TableRow.LayoutParams.WRAP_CONTENT;
		btn.setLayoutParams(params);
		btn.setImageResource(R.drawable.home);
		btn.setBackgroundResource(R.drawable.home);
		btn.setId(dynamicBtnID);
		btn.setOnClickListener(getClickListener());
		idList.add(btn);
		return btn;
	}

}