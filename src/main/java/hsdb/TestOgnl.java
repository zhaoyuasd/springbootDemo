package hsdb;

import ognl.*;

/**
 * @author dongli
 * @create 2023/10/24 11:23
 * @desc
 */

public class TestOgnl {
    private static final MemberAccess MEMBER_ACCESS = new DefaultMemberAccess(true);
    public static void main(String[] args) throws OgnlException {
        OgnlContext context = new OgnlContext();
        context.setClassResolver(new DefaultClassResolver());
        context.setMemberAccess(MEMBER_ACCESS);
        Ognl.getValue("fn()", context, new Test());
    }
}
