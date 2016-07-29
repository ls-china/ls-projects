package com.ls.user.realm;

import java.io.Serializable;

public interface IRole extends Serializable {

    public String getName();

    public void grantResource(IResource resource, IResource.AccessType accessType);

}