import React, { useState, useEffect } from 'react';
import DashboardLayout from '../components/DashboardLayout';
import api from '../services/api';
import { useAuth } from '../context/AuthContext';
import { useTheme } from '../context/ThemeContext';
import { BarChart3, Clock, CheckCircle, Plus, PieChart as PieChartIcon } from 'lucide-react';
import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer, Cell } from 'recharts';

const PollsPage = () => {
    const [polls, setPolls] = useState([]);
    const [showCreate, setShowCreate] = useState(false);
    const [loading, setLoading] = useState(true);
    const [newPoll, setNewPoll] = useState({ question: '', options: ['', ''] });
    const { user } = useAuth();
    const { theme } = useTheme();

    useEffect(() => {
        fetchPolls();
    }, []);

    const fetchPolls = async () => {
        try {
            const response = await api.get('/polls');
            setPolls(response.data);
        } catch (err) {
            console.error('Failed to fetch polls', err);
        } finally {
            setLoading(false);
        }
    };

    const handleVote = async (optionId) => {
        try {
            await api.post(`/polls/${optionId}/vote`);
            fetchPolls();
        } catch (err) {
            alert('Failed to vote');
        }
    };

    const handleCreate = async (e) => {
        e.preventDefault();
        try {
            await api.post('/polls', newPoll);
            setShowCreate(false);
            setNewPoll({ question: '', options: ['', ''] });
            fetchPolls();
        } catch (err) {
            alert('Failed to create poll');
        }
    };

    const COLORS = ['#6366f1', '#a855f7', '#ec4899', '#f43f5e', '#f59e0b'];

    return (
        <DashboardLayout title="Sondages & Enquêtes">
            <div className="flex flex-col gap-8 max-w-5xl mx-auto">
                <div className="flex items-center justify-between">
                    <p className="text-slate-500 font-medium italic">Votre avis compte. Chaque vote contribue à une meilleure université.</p>
                    {user.role !== 'ROLE_ETUDIANT' && (
                        <button onClick={() => setShowCreate(true)} className="btn-primary flex items-center gap-2">
                            <Plus size={18} />
                            <span>Créer un Sondage</span>
                        </button>
                    )}
                </div>

                {showCreate && (
                    <div className="card shadow-lg ring-2 ring-primary-100">
                        <h3 className="text-xl font-bold mb-6">Créer un Nouveau Sondage</h3>
                        <form onSubmit={handleCreate} className="space-y-6">
                            <div className="space-y-1">
                                <label className="text-sm font-bold text-slate-700">Question</label>
                                <input
                                    className="input"
                                    placeholder="ex: Devrions-nous déplacer l'examen final à Juillet ?"
                                    value={newPoll.question}
                                    onChange={(e) => setNewPoll({ ...newPoll, question: e.target.value })}
                                    required
                                />
                            </div>
                            <div className="space-y-3">
                                <label className="text-sm font-bold text-slate-700">Options</label>
                                {newPoll.options.map((opt, idx) => (
                                    <input
                                        key={idx}
                                        className="input"
                                        placeholder={`Option ${idx + 1}`}
                                        value={opt}
                                        onChange={(e) => {
                                            const opts = [...newPoll.options];
                                            opts[idx] = e.target.value;
                                            setNewPoll({ ...newPoll, options: opts });
                                        }}
                                        required
                                    />
                                ))}
                                <button
                                    type="button"
                                    onClick={() => setNewPoll({ ...newPoll, options: [...newPoll.options, ''] })}
                                    className="text-primary-600 text-sm font-bold hover:underline"
                                >
                                    + Ajouter plus d'options
                                </button>
                            </div>
                            <div className="flex justify-end gap-3 pt-4 border-t border-slate-50">
                                <button type="button" onClick={() => setShowCreate(false)} className="px-6 py-2 text-slate-600 font-medium hover:bg-slate-50 rounded-lg">Annuler</button>
                                <button type="submit" className="btn-primary px-10">Lancer le Sondage</button>
                            </div>
                        </form>
                    </div>
                )}

                <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
                    {loading ? (
                        <div className="col-span-full py-20 flex justify-center"><div className="animate-spin rounded-full h-10 w-10 border-b-2 border-primary-600"></div></div>
                    ) : polls.map((poll) => (
                        <div key={poll.id} className="card h-full flex flex-col hover:shadow-lg transition-shadow">
                            <div className="flex items-center gap-2 text-primary-600 dark:text-primary-400 mb-4 tracking-tighter uppercase font-bold text-[10px]">
                                <Clock size={12} />
                                Sondage en cours
                            </div>
                            <h4 className="text-xl font-bold text-slate-800 dark:text-slate-100 mb-8 leading-tight">{poll.question}</h4>

                            <div className="space-y-3 flex-1">
                                {poll.options.map((opt) => (
                                    <button
                                        key={opt.id}
                                        onClick={() => handleVote(opt.id)}
                                        className="w-full text-left p-4 rounded-2xl bg-slate-50 dark:bg-slate-800/50 border border-slate-100 dark:border-slate-800 hover:border-primary-300 dark:hover:border-primary-700 hover:bg-white dark:hover:bg-slate-800 transition-all group relative overflow-hidden"
                                    >
                                        <div className="flex items-center justify-between relative z-10">
                                            <span className="font-medium text-slate-700 dark:text-slate-300 group-hover:text-primary-600 dark:group-hover:text-primary-400">{opt.text}</span>
                                            <span className="text-sm font-bold text-slate-400 dark:text-slate-500 group-hover:text-primary-500">{opt.votes} votes</span>
                                        </div>
                                    </button>
                                ))}
                            </div>

                            <div className="mt-8 h-48 w-full border-t border-slate-50 dark:border-slate-800 pt-6">
                                <ResponsiveContainer width="100%" height="100%">
                                    <BarChart data={poll.options} layout="vertical">
                                        <CartesianGrid strokeDasharray="3 3" horizontal={false} stroke={theme === 'dark' ? '#1e293b' : '#f1f5f9'} />
                                        <XAxis type="number" hide />
                                        <YAxis dataKey="text" type="category" width={80} tick={{ fontSize: 10, fontWeight: 600, fill: theme === 'dark' ? '#94a3b8' : '#64748b' }} />
                                        <Tooltip cursor={{ fill: 'transparent' }} contentStyle={{ borderRadius: '12px', background: theme === 'dark' ? '#0f172a' : '#fff', border: 'none', boxShadow: '0 4px 12px rgba(0,0,0,0.1)', color: theme === 'dark' ? '#f1f5f9' : '#1e293b' }} />
                                        <Bar dataKey="votes" radius={[0, 4, 4, 0]} barSize={20}>
                                            {poll.options.map((entry, index) => (
                                                <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
                                            ))}
                                        </Bar>
                                    </BarChart>
                                </ResponsiveContainer>
                            </div>
                        </div>
                    ))}
                </div>
            </div>
        </DashboardLayout>
    );
};

export default PollsPage;
