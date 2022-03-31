package com.mynimef.swiracle.repository;

import com.mynimef.swiracle.models.UserInit;

class RepositoryApp {
    private UserInit account;

    final protected synchronized void setAccount(UserInit account) {
        this.account = account;
    }

    final protected synchronized UserInit getAccount() {
        return account;
    }
}
