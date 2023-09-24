package anish.tutorial.toc.ui.hints;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import anish.tutorial.toc.R;
import anish.tutorial.toc.ui.slideshow.SlideshowViewModel;

public class HintFragment extends Fragment {
    private HintViewModel hintViewModel;
    String text ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        hintViewModel =
                ViewModelProviders.of(this).get(HintViewModel.class);
        View root = inflater.inflate(R.layout.fragment_hint,container,false);
        final TextView hints = root.findViewById(R.id.hintView);
        text = "\n\t\t\t\tConverting to CNF \n\n" +
                "Firstly Symbols and Variables are inserted one by one\n\n" +
                "For eg: if S->AAC/AC/D then in application we follow like\n\n" +
                "S in Symbol and AAC in variable and press on SET for saving production " +
                "and again S and AC and SET and similar S and D is SET\n\n" +
                " After enter all the variables and symbol we press on calculate\n\n" +
                "For Null Production 'E' is used and 'E' is not used as symbol\n\n" +
                "Here + - * / special character cannot be used and other special character also cannot be used\n\n" +
                "In production question any letter (A-Z)/(a-z) and numbers (0-9) can be used\n\n" +
                "RESET button empty or clears the data\n\n" +
                "There is also suggestion in variable after word is entered and calculated\n\n";
        hints.setText(text);
        hints.setTextIsSelectable(true);
        return root;
    }
}
