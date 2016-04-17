package com.github.piasy.safelyandroid.lint;

import com.android.tools.lint.client.api.IssueRegistry;
import com.android.tools.lint.detector.api.Issue;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Piasy{github.com/Piasy} on 16/4/16.
 */
public class SafelyAndroidRegistry extends IssueRegistry {
    @Override
    public List<Issue> getIssues() {
        return Arrays.asList(UnsafeAndroidDetector.ISSUE_UNSAFE_DISMISS,
                UnsafeAndroidDetector.ISSUE_UNSAFE_COMMIT);
    }
}
