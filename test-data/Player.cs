using System;
using System.Collections.Generic;

namespace SportsLeague.Data.Models;

public partial class Player
{
    public int PlayerId { get; set; }

    public string FullName { get; set; } = null!;

    public int? JerseyNumber { get; set; }

    public string? Position { get; set; }

    public DateOnly? DateOfBirth { get; set; }

    public int? TeamId { get; set; }

    public virtual ICollection<PlayerMatchStat> PlayerMatchStats { get; set; } = new List<PlayerMatchStat>();

    public virtual Team? Team { get; set; }
}
