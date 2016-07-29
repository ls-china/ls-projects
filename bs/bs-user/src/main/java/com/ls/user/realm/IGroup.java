package com.ls.user.realm;

import java.util.List;

public interface IGroup extends IRole {

    public List<IRole> getGroupMembers();

    public boolean hasMember(IRole role);

    public void addMember(IRole role);

    public void addMembers(IRole ... roles);

    public void addMembers(List<IRole> roles);

    public void removeMember(IRole role);

    public void removeMembers(IRole ... roles);

    public void removeMembers(List<IRole> roles);

}
