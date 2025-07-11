import React, {useState, useEffect} from "react";
import MemberForm from "./MemberForm";
import MemberList from "./MemberList";
import {getAllMembers, addMember, updateMember, deleteMember } from "../../services/MembersService";

const ManageMembers = () => {
    const [members, setMembers] = useState([]);
    const [selectedMember, setSelectedMember] = useState(null);
    const loadMembers = async () => {
        const result = await getAllMembers();
        setMembers(result);
    }

    useEffect(()=>{loadMembers();}, []);

    const handleAddorUpdate = async (member) => {
        if(member.memberId) {
            await updateMember(member);
        }else{
            await addMember(member);
        }
        setSelectedMember(null);
        loadMembers();
    }

    const handleDelete = async(id) => {
        await deleteMember(id);
        loadMembers();
    }
    return (
         <div className="max-w-4xl mx-auto mt-8 bg-gray-700 text-gray-200">
      <h2 className="text-xl font-bold mb-4 text-center">👤 Librarium365 - Member Management Dashboard</h2>
      <MemberForm onSubmit = {handleAddorUpdate} selectedMember = {selectedMember} clearSelection = {() => setSelectedMember(null)}/>
        <MemberList members = {members} onEdit = {setSelectedMember} onDelete = {handleDelete}/>
      </div>
    )
}
export default ManageMembers;